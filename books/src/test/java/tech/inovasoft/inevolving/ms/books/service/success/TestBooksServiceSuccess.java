package tech.inovasoft.inevolving.ms.books.service.success;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TestBooksServiceSuccess {

    @Mock
    private BooksRepository repository;

    @InjectMocks
    private BooksService service;

    @Test
    public void addBook() throws NotSavedDTOInDbException {

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
        when(repository.save(any(Book.class))).thenReturn(expectedBook);
        var resultBook = service.addBook(idUser, dto);

        // Then (Então)
        assertNotNull(resultBook);
        assertEquals(expectedBook.getId(), resultBook.getId());
        assertEquals(expectedBook.getTitle(), resultBook.getTitle());
        assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
        assertEquals(expectedBook.getTheme(), resultBook.getTheme());
        assertEquals(expectedBook.getStatus(), resultBook.getStatus());
        assertEquals(expectedBook.getCoverImage(), resultBook.getCoverImage());
        assertEquals(expectedBook.getIdUser(), resultBook.getIdUser());

        verify(repository, times(1)).save(any(Book.class));
    }

}
