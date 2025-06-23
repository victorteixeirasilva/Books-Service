package tech.inovasoft.inevolving.ms.books.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.inovasoft.inevolving.ms.books.domain.dto.response.ExceptionResponse;
import tech.inovasoft.inevolving.ms.books.domain.exception.BookNotFoundException;
import tech.inovasoft.inevolving.ms.books.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.exception.UnauthorizedUserAboutBookException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotSavedDTOInDbException.class)
    private ResponseEntity<ExceptionResponse> notSavedDTOInDbException(NotSavedDTOInDbException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(UnauthorizedUserAboutBookException.class)
    private ResponseEntity<ExceptionResponse> unauthorizedUserAboutBookException(UnauthorizedUserAboutBookException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(BookNotFoundException.class)
    private ResponseEntity<ExceptionResponse> bookNotFoundException(BookNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(DataBaseException.class)
    private ResponseEntity<ExceptionResponse> dataBaseException(DataBaseException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(
                        exception.getClass().getSimpleName(),
                        exception.getMessage()
                ));
    }

}
