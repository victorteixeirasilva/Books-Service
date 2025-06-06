package tech.inovasoft.inevolving.ms.books.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BooksControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void addBook_ok() {
        //TODO: Desenvolver teste do End-Point
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
