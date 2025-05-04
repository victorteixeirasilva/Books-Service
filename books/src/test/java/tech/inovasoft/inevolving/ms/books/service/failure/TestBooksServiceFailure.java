package tech.inovasoft.inevolving.ms.books.service.failure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;
import tech.inovasoft.inevolving.ms.books.repository.BooksRepository;
import tech.inovasoft.inevolving.ms.books.service.BooksService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestBooksServiceFailure {

    @Mock
    private BooksRepository repository;

    @InjectMocks
    private BooksService service;

    @Test
    public void addBookNotSavedDTOInDbException() throws NotSavedDTOInDbException {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title",
                "Author",
                "Theme",
                "CoverImage"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                idUser
        );

        var newBook = new Book(
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                idUser
        );


        // When (Quando)
        when(repository.save(any(Book.class))).thenThrow(new RuntimeException());
        Exception exception = assertThrows(NotSavedDTOInDbException.class, () -> {
            service.addBook(idUser, dto);
        });

        // Then (Então)
        assertEquals(
                "It was not possible to save the book in the database, we probably had some difficulty with the integration with the database",
                exception.getMessage()
        );

        verify(repository, times(1)).save(any(Book.class));
    }


}
