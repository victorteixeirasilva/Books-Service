package tech.inovasoft.inevolving.ms.books.domain.exception;

public class UnauthorizedUserAboutBookException extends Exception {
    public UnauthorizedUserAboutBookException() {
        super("Unauthorized User About the Book Reported");
    }

    public UnauthorizedUserAboutBookException(String message) {
        super(message);
    }
}
