package tech.inovasoft.inevolving.ms.books.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BooksControllerTest {

    @LocalServerPort
    private int port;

    UUID idUser = UUID.randomUUID();

    @Test
    public void addBook_ok() {
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
    }

    @Test
    public void updateBook_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void updateBookStatusToDo_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void updateBookStatusInProgress_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void updateBookStatusCompleted_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void deleteBook_ok() {
        //TODO: Desenvolver teste do End-Point
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
