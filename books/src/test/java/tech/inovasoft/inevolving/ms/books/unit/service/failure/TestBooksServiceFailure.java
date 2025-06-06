package tech.inovasoft.inevolving.ms.books.unit.service.failure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.BookNotFoundException;
import tech.inovasoft.inevolving.ms.books.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.exception.UnauthorizedUserAboutBookException;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;
import tech.inovasoft.inevolving.ms.books.repository.BooksRepository;
import tech.inovasoft.inevolving.ms.books.service.BooksService;

import java.util.ArrayList;
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
    public void addBookNotSavedDTOInDbException() {
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

    @Test
    public void updateBookDataBaseExceptionInFindById() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenThrow(new RuntimeException());
        Exception exception = assertThrows(DataBaseException.class, () -> {
            service.updateBook(idUser, expectedBook.getId(), dto);
        });

        // Then (Então)
        assertEquals("Error in integration with Database (findById)", exception.getMessage());

        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateBookDataBaseExceptionInSave() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                idUser
        );

        // When (Quando)
        when(repository.save(any(Book.class))).thenThrow(new RuntimeException());
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(oldBook));
        var exception = assertThrows(DataBaseException.class, () -> {
            service.updateBook(idUser, expectedBook.getId(), dto);
        });

        // Then (Então)
        assertEquals("Error in integration with Database (save)", exception.getMessage());

        verify(repository, times(1)).save(any(Book.class));
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateBookBookNotFoundException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                idUser
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        var exception = assertThrows(BookNotFoundException.class, () -> {
            service.updateBook(idUser, UUID.randomUUID(), dto);
        });

        // Then (Então)
        assertEquals("Book not found in database", exception.getMessage());

        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateBookUnauthorizedUserAboutBook() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(oldBook));
        var exception = assertThrows(UnauthorizedUserAboutBookException.class, () -> {
            service.updateBook(idUser, expectedBook.getId(), dto);
        });

        // Then (Então)
        assertEquals("Unauthorized User About the Book Reported", exception.getMessage());

        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateBookStatusDataBaseExceptionFindById() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenThrow(new RuntimeException());
        var exception = assertThrows(DataBaseException.class, () -> {
            service.updateBookStatus(idUser, expectedBook.getId(), Status.TO_DO);
        });

        // Then (Então)
        assertEquals("Error in integration with Database (findById)", exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void updateBookStatusBookNotFoundException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        var exception = assertThrows(BookNotFoundException.class, () -> {
            service.updateBookStatus(idUser, expectedBook.getId(), Status.TO_DO);
        });

        // Then (Então)
        assertEquals("Book not found in database", exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void updateBookStatusUnauthorizedUserAboutBookException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                UUID.randomUUID()
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(expectedBook));
        var exception = assertThrows(UnauthorizedUserAboutBookException.class, () -> {
            service.updateBookStatus(idUser, expectedBook.getId(), Status.TO_DO);
        });

        // Then (Então)
        assertEquals(new UnauthorizedUserAboutBookException().getMessage(), exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void updateBookStatusDataBaseExceptionSave() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(expectedBook));
        when(repository.save(any(Book.class))).thenThrow(new RuntimeException());
        var exception = assertThrows(DataBaseException.class, () -> {
            service.updateBookStatus(idUser, expectedBook.getId(), Status.TO_DO);
        });

        // Then (Então)
        assertEquals(new DataBaseException().getMessage() + " (save)", exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void deleteBookDataBaseExceptionFindById() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenThrow(new RuntimeException());
        var exception = assertThrows(DataBaseException.class, () -> {
            service.deleteBook(idUser, expectedBook.getId());
        });

        // Then (Então)
        assertEquals(new DataBaseException().getMessage() + " (findById)", exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void deleteBookBookNotFoundException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        var exception = assertThrows(BookNotFoundException.class, () -> {
            service.deleteBook(idUser, expectedBook.getId());
        });

        // Then (Então)
        assertEquals(new BookNotFoundException().getMessage(), exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void deleteBookUnauthorizedUserAboutBookException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                UUID.randomUUID()
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(expectedBook));
        var exception = assertThrows(UnauthorizedUserAboutBookException.class, () -> {
            service.deleteBook(idUser, expectedBook.getId());
        });

        // Then (Então)
        assertEquals(new UnauthorizedUserAboutBookException().getMessage(), exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void deleteBookDataBaseExceptionDelete() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(expectedBook));
        doThrow(new RuntimeException()).when(repository).delete(any(Book.class));
        var exception = assertThrows(DataBaseException.class, () -> {
            service.deleteBook(idUser, expectedBook.getId());
        });

        // Then (Então)
        assertEquals(new DataBaseException().getMessage() + " (delete)", exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
        verify(repository, times(1)).delete(expectedBook);
    }

    @Test
    public void getBooksDataBaseException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findAllByUserId(any(UUID.class))).thenThrow(new RuntimeException());
        var exception = assertThrows(DataBaseException.class, () -> {
            service.getBooks(idUser);
        });

        // Then (Então)
        assertEquals(new DataBaseException().getMessage() + " (findAllByUserId)", exception.getMessage());

        verify(repository, times(1)).findAllByUserId(idUser);
    }

    @Test
    public void getBooksBookNotFoundException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findAllByUserId(any(UUID.class))).thenReturn(new ArrayList<>());
        var exception = assertThrows(BookNotFoundException.class, () -> {
            service.getBooks(idUser);
        });

        // Then (Então)
        assertEquals("User does not have any registered books.", exception.getMessage());

        verify(repository, times(1)).findAllByUserId(idUser);
    }

    @Test
    public void getBooksStatusDataBaseException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findAllByUserIdAndStatus(any(UUID.class), any(String.class))).thenThrow(new RuntimeException());
        var exception = assertThrows(DataBaseException.class, () -> {
            service.getBooksStatus(idUser, Status.TO_DO);
        });

        // Then (Então)
        assertEquals(new DataBaseException().getMessage() + " (findAllByUserIdAndStatus)", exception.getMessage());

        verify(repository, times(1)).findAllByUserIdAndStatus(idUser, Status.TO_DO);
    }

    @Test
    public void getBooksStatusBookNotFoundException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findAllByUserIdAndStatus(any(UUID.class), any(String.class))).thenReturn(new ArrayList<>());
        var exception = assertThrows(BookNotFoundException.class, () -> {
            service.getBooksStatus(idUser, Status.TO_DO);
        });

        // Then (Então)
        assertEquals("The user does not have any registered books, with the status (" + Status.TO_DO + ").", exception.getMessage());

        verify(repository, times(1)).findAllByUserIdAndStatus(idUser, Status.TO_DO);
    }

    @Test
    public void getBookDataBaseException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenThrow(new RuntimeException());
        var exception = assertThrows(DataBaseException.class, () -> {
            service.getBook(idUser, expectedBook.getId());
        });

        // Then (Então)
        assertEquals(new DataBaseException().getMessage() + " (findById)", exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void getBookBookNotFoundException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        var exception = assertThrows(BookNotFoundException.class, () -> {
            service.getBook(idUser, expectedBook.getId());
        });

        // Then (Então)
        assertEquals(new BookNotFoundException().getMessage(), exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

    @Test
    public void getBookUnauthorizedUserAboutBookException() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var dto = new RequestBookDTO(
                "title2",
                "Author2",
                "Theme2",
                "CoverImage2"
        );

        var expectedBook = new Book(
                UUID.randomUUID(),
                dto.title(),
                dto.author(),
                dto.theme(),
                Status.TO_DO,
                dto.coverImage(),
                UUID.randomUUID()
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                UUID.randomUUID()
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(expectedBook));
        var exception = assertThrows(UnauthorizedUserAboutBookException.class, () -> {
            service.getBook(idUser, expectedBook.getId());
        });

        // Then (Então)
        assertEquals(new UnauthorizedUserAboutBookException().getMessage(), exception.getMessage());

        verify(repository, times(1)).findById(expectedBook.getId());
    }

}
