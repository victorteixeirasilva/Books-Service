package tech.inovasoft.inevolving.ms.books.domain.exception;

public class UnauthorizedUserAboutBook extends Exception {
    public UnauthorizedUserAboutBook() {
        super("Unauthorized User About the Book Reported");
    }

    public UnauthorizedUserAboutBook(String message) {
        super(message);
    }
}
