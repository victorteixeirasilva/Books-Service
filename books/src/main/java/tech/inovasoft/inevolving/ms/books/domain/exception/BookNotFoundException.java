package tech.inovasoft.inevolving.ms.books.domain.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("Book not found in database");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
