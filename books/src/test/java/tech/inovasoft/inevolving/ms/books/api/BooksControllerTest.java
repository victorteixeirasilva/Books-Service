package tech.inovasoft.inevolving.ms.books.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;

import java.util.Objects;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BooksControllerTest {

    @LocalServerPort
    private int port;

    UUID idUser = UUID.randomUUID();

    private String addBook(){
        RequestBookDTO requestBookDTO = new RequestBookDTO(
                "Title",
                "Author",
                "Theme",
                "https://inovasoft.tech/wp-content/webp-express/webp-images/uploads/2025/03/LogoTipoBorda-512x319px.png.webp"
        );

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(requestBookDTO)
                .when()
                .post("http://localhost:" + port + "/ms/books/" + idUser)
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("id", Matchers.notNullValue()).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("title", equalTo(requestBookDTO.title())).and()
                .body("author", equalTo(requestBookDTO.author())).and()
                .body("theme", equalTo(requestBookDTO.theme())).and()
                .body("status", equalTo(Status.TO_DO))
                .body("coverImage", equalTo(requestBookDTO.coverImage()));

        return response.extract().body().jsonPath().get("id");
    }

    private boolean deleteBook(UUID idUser, UUID idBook) {

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .delete("http://localhost:" + port + "/ms/books/" + idUser + "/" + idBook)
                .then();

        // Valida a resposta
        response.assertThat().statusCode(200);

        return response.extract().statusCode() == 200;
    }

    private Book getBook(UUID idUser, UUID idBook) {
        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .get("http://localhost:" + port + "/ms/books/" + idUser + "/" + idBook)
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200);

        return new Book(
                UUID.fromString(response.extract().body().jsonPath().get("id")),
                response.extract().body().jsonPath().get("title"),
                response.extract().body().jsonPath().get("author"),
                response.extract().body().jsonPath().get("theme"),
                response.extract().body().jsonPath().get("status"),
                response.extract().body().jsonPath().get("coverImage"),
                UUID.fromString(response.extract().body().jsonPath().get("idUser"))
        );
    }

    @Test
    public void addBook_ok() {
        String idBookString = addBook();
        boolean result = false;
        UUID idBook = null;
        if (!Objects.equals(idBookString, "")) {
            idBook = UUID.fromString(idBookString);
            result = true;
        }
        Assertions.assertTrue(result);
        deleteBook(idUser, idBook);
    }

    @Test
    public void updateBook_ok() {
        UUID idBook = UUID.fromString(addBook());

        var updateBook = new RequestBookDTO("update title", "update author", "update theme", "update cover image");

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(updateBook)
                .when()
                .put("http://localhost:" + port + "/ms/books/" + idUser + "/" + idBook)
                .then();

        Book book = getBook(idUser, idBook);

        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("title", equalTo(updateBook.title())).and()
                .body("author", equalTo(updateBook.author())).and()
                .body("theme", equalTo(updateBook.theme()));

        Assertions.assertEquals(updateBook.title(), book.getTitle());
        Assertions.assertEquals(updateBook.author(), book.getAuthor());
        Assertions.assertEquals(updateBook.coverImage(), book.getCoverImage());
        Assertions.assertEquals(updateBook.theme(), book.getTheme());
        Assertions.assertTrue(deleteBook(idUser, idBook));
    }

    @Test
    public void updateBookStatusToDo_ok() {

        UUID idBook = UUID.fromString(addBook());

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        ValidatableResponse responseProgress = requestSpecification
                .when()
                .patch("http://localhost:" + port + "/ms/books/status/progress/" + idUser + "/" + idBook)
                .then();

        responseProgress.assertThat().statusCode(200);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .patch("http://localhost:" + port + "/ms/books/status/todo/" + idUser + "/" + idBook)
                .then();

        Book book = getBook(idUser, idBook);

        // Valida a resposta
        response.assertThat().statusCode(200);

        Assertions.assertEquals(Status.TO_DO, book.getStatus());
        Assertions.assertTrue(deleteBook(idUser, idBook));
    }

    @Test
    public void updateBookStatusInProgress_ok() {
        UUID idBook = UUID.fromString(addBook());

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .patch("http://localhost:" + port + "/ms/books/status/progress/" + idUser + "/" + idBook)
                .then();

        Book book = getBook(idUser, idBook);

        // Valida a resposta
        response.assertThat().statusCode(200);

        Assertions.assertEquals(Status.IN_PROGRESS, book.getStatus());
        Assertions.assertTrue(deleteBook(idUser, idBook));
    }

    @Test
    public void updateBookStatusCompleted_ok() {
        UUID idBook = UUID.fromString(addBook());

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .patch("http://localhost:" + port + "/ms/books/status/completed/" + idUser + "/" + idBook)
                .then();

        Book book = getBook(idUser, idBook);

        // Valida a resposta
        response.assertThat().statusCode(200);

        Assertions.assertEquals(Status.COMPLETED, book.getStatus());
        Assertions.assertTrue(deleteBook(idUser, idBook));
    }

    @Test
    public void deleteBook_ok() {
        String idBookString = addBook();
        boolean result = false;
        UUID idBook = null;
        if (!Objects.equals(idBookString, "")) {
            idBook = UUID.fromString(idBookString);
            result = deleteBook(idUser, idBook);
            Assertions.assertTrue(result);
        }

    }

    @Test
    public void getBooks_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void getBooksToDo_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void getBooksInProgress_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void getBooksCompleted_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void getBook_ok() {
        //TODO: Desenvolver teste do End-Point
    }

}
