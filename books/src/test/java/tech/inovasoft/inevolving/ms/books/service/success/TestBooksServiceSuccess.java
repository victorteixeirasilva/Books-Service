package tech.inovasoft.inevolving.ms.books.service.success;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Test
    public void updateBook() throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
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
        when(repository.save(any(Book.class))).thenReturn(expectedBook);
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(oldBook));
        var resultBook = service.updateBook(idUser, expectedBook.getId(), dto);

        // Then (Então)
        assertNotNull(resultBook);
        assertEquals(expectedBook.getId(), resultBook.getId());
        assertEquals(dto.title(), resultBook.getTitle());
        assertEquals(dto.author(), resultBook.getAuthor());
        assertEquals(dto.theme(), resultBook.getTheme());
        assertEquals(expectedBook.getStatus(), resultBook.getStatus());
        assertEquals(dto.coverImage(), resultBook.getCoverImage());
        assertEquals(expectedBook.getIdUser(), resultBook.getIdUser());

        verify(repository, times(1)).save(any(Book.class));
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateBookStatusToDo() throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var expectedBook = new Book(
                UUID.randomUUID(),
                "title",
                "Author",
                "Theme",
                Status.TO_DO,
                "cover image",
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.COMPLETED,
                "cover image",
                idUser
        );

        // When (Quando)
        when(repository.save(any(Book.class))).thenReturn(expectedBook);
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(oldBook));
        var resultBook = service.updateBookStatus(idUser, expectedBook.getId(), Status.TO_DO);

        // Then (Então)
        assertNotNull(resultBook);
        assertEquals(expectedBook.getId(), resultBook.getId());
        assertEquals(expectedBook.getTitle(), resultBook.getTitle());
        assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
        assertEquals(expectedBook.getTheme(), resultBook.getTheme());
        assertEquals(Status.TO_DO, resultBook.getStatus());
        assertEquals(expectedBook.getCoverImage(), resultBook.getCoverImage());
        assertEquals(expectedBook.getIdUser(), resultBook.getIdUser());

        verify(repository, times(1)).save(any(Book.class));
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateBookStatusInProgress() throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var expectedBook = new Book(
                UUID.randomUUID(),
                "title",
                "Author",
                "Theme",
                Status.IN_PROGRESS,
                "cover image",
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
        when(repository.save(any(Book.class))).thenReturn(expectedBook);
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(oldBook));
        var resultBook = service.updateBookStatus(idUser, expectedBook.getId(), Status.IN_PROGRESS);

        // Then (Então)
        assertNotNull(resultBook);
        assertEquals(expectedBook.getId(), resultBook.getId());
        assertEquals(expectedBook.getTitle(), resultBook.getTitle());
        assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
        assertEquals(expectedBook.getTheme(), resultBook.getTheme());
        assertEquals(Status.IN_PROGRESS, resultBook.getStatus());
        assertEquals(expectedBook.getCoverImage(), resultBook.getCoverImage());
        assertEquals(expectedBook.getIdUser(), resultBook.getIdUser());

        verify(repository, times(1)).save(any(Book.class));
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateBookStatusCompleted() throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var expectedBook = new Book(
                UUID.randomUUID(),
                "title",
                "Author",
                "Theme",
                Status.COMPLETED,
                "cover image",
                idUser
        );

        var oldBook = new Book(
                expectedBook.getId(),
                "title",
                "Author",
                "Theme",
                Status.IN_PROGRESS,
                "cover image",
                idUser
        );

        // When (Quando)
        when(repository.save(any(Book.class))).thenReturn(expectedBook);
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(oldBook));
        var resultBook = service.updateBookStatus(idUser, expectedBook.getId(), Status.COMPLETED);

        // Then (Então)
        assertNotNull(resultBook);
        assertEquals(expectedBook.getId(), resultBook.getId());
        assertEquals(expectedBook.getTitle(), resultBook.getTitle());
        assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
        assertEquals(expectedBook.getTheme(), resultBook.getTheme());
        assertEquals(Status.COMPLETED, resultBook.getStatus());
        assertEquals(expectedBook.getCoverImage(), resultBook.getCoverImage());
        assertEquals(expectedBook.getIdUser(), resultBook.getIdUser());

        verify(repository, times(1)).save(any(Book.class));
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void deleteBook() throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
        // Given (Dado)
        var idUser = UUID.randomUUID();
        var idBook = UUID.randomUUID();

        var oldBook = new Book(
                idBook,
                "title",
                "Author",
                "Theme",
                Status.COMPLETED,
                "cover image",
                idUser
        );

        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(oldBook));
        var resultBook = service.deleteBook(idUser, idBook);

        // Then (Então)
        assertNotNull(resultBook);
        assertEquals("Livro deletado.", resultBook.message());

        verify(repository, times(1)).findById(idBook);
        verify(repository, times(1)).delete(oldBook);
    }

    @Test
    public void getBooks() throws BookNotFoundException, DataBaseException {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        List<Book> mockBookList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            var mockBook = new Book(
                    UUID.randomUUID(),
                    "title",
                    "Author",
                    "Theme",
                    Status.COMPLETED,
                    "cover image",
                    idUser
            );

            mockBookList.add(mockBook);

        }


        // When (Quando)
        when(repository.findAllByUserId(any(UUID.class))).thenReturn(mockBookList);
        var bookList = service.getBooks(idUser);

        // Then (Então)
        assertNotNull(bookList);
        assertEquals(10, bookList.size());

        verify(repository, times(1)).findAllByUserId(idUser);
    }

    @Test
    public void getBooksStatus() throws BookNotFoundException, DataBaseException {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        List<Book> mockBookList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            var mockBookTd = new Book(
                    UUID.randomUUID(),
                    "title",
                    "Author",
                    "Theme",
                    Status.TO_DO,
                    "cover image",
                    idUser
            );

            mockBookList.add(mockBookTd);
        }


        // When (Quando)
        when(repository.findAllByUserIdAndStatus(any(UUID.class), any(String.class))).thenReturn(mockBookList);
        var bookList = service.getBooksStatus(idUser, Status.TO_DO);

        // Then (Então)
        assertNotNull(bookList);
        assertEquals(10, bookList.size());
        assertEquals(Status.TO_DO, bookList.get(1).getStatus());

        verify(repository, times(1)).findAllByUserIdAndStatus(idUser, Status.TO_DO);
    }

    @Test
    public void getBook() {
        // Given (Dado)
        var idUser = UUID.randomUUID();

        var idBook = UUID.randomUUID();

        var expectedBook = new Book(
                idBook,
                "title",
                "Author",
                "Theme",
                Status.COMPLETED,
                "cover image",
                idUser
        );


        // When (Quando)
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(expectedBook));
        var resultBook = service.getBook(idUser, idBook);

        // Then (Então)
        assertNotNull(resultBook);
        assertEquals(expectedBook.getId(), resultBook.getId());
        assertEquals(expectedBook.getIdUser(), resultBook.getIdUser());
        assertEquals(expectedBook.getTitle(), resultBook.getTitle());
        assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
        assertEquals(expectedBook.getStatus(), resultBook.getStatus());
        assertEquals(expectedBook.getCoverImage(), resultBook.getCoverImage());

        verify(repository, times(1)).findById(idBook);
    }

}
