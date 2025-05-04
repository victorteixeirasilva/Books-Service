package tech.inovasoft.inevolving.ms.books.domain.exception;

public class NotSavedDTOInDbException extends Exception {
    public NotSavedDTOInDbException() {
        super("It was not possible to save the book in the database, we probably had some difficulty with the integration with the database");
    }

    public NotSavedDTOInDbException(String message) {
        super(message);
    }
}
