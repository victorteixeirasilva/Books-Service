package tech.inovasoft.inevolving.ms.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.BookNotFoundException;
import tech.inovasoft.inevolving.ms.books.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.exception.UnauthorizedUserAboutBookException;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;
import tech.inovasoft.inevolving.ms.books.repository.BooksRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class BooksService {

    @Autowired
    private BooksRepository repository;

    public Book addBook(UUID idUser, RequestBookDTO dto) throws NotSavedDTOInDbException {
        try {
            return repository.save(new Book(idUser, dto));
        } catch (Exception e) {
            throw new NotSavedDTOInDbException();
        }
    }

    public Book updateBook(UUID idUser, UUID idBook, RequestBookDTO dto) throws DataBaseException, BookNotFoundException, UnauthorizedUserAboutBookException {
        Optional<Book> optOldBook = Optional.empty();
        try {
            optOldBook = repository.findById(idBook);
        } catch (Exception e) {
            throw new DataBaseException("(findById)");
        }

        if (optOldBook.isEmpty()){
            throw new BookNotFoundException();
        }

        if (!optOldBook.get().getIdUser().equals(idUser)){
            throw new UnauthorizedUserAboutBookException();
        }

        var newBook = new Book(
                optOldBook.get().getId(),
                dto.title(),
                dto.author(),
                dto.theme(),
                optOldBook.get().getStatus(),
                dto.coverImage(),
                idUser
        );

        try {
            return repository.save(newBook);
        } catch (Exception e) {
            throw new DataBaseException("(save)");
        }
    }

    public Book updateBookStatusToDo(UUID idUser, UUID idBook) throws DataBaseException, BookNotFoundException, UnauthorizedUserAboutBookException {
        Optional<Book> optOldBook = Optional.empty();
        try {
            optOldBook = repository.findById(idBook);
        } catch (Exception e) {
            throw new DataBaseException("(findById)");
        }

        if (optOldBook.isEmpty()){
            throw new BookNotFoundException();
        }

        if (!optOldBook.get().getIdUser().equals(idUser)){
            throw new UnauthorizedUserAboutBookException();
        }

        var newBook = new Book(
                optOldBook.get().getId(),
                optOldBook.get().getTitle(),
                optOldBook.get().getAuthor(),
                optOldBook.get().getTheme(),
                Status.TO_DO,
                optOldBook.get().getCoverImage(),
                idUser
        );

        try {
            return repository.save(newBook);
        } catch (Exception e) {
            throw new DataBaseException("(save)");
        }
    }

    public Book updateBookStatusInProgress(UUID idUser, UUID idBook) throws DataBaseException, BookNotFoundException, UnauthorizedUserAboutBookException {
        Optional<Book> optOldBook = Optional.empty();
        try {
            optOldBook = repository.findById(idBook);
        } catch (Exception e) {
            throw new DataBaseException("(findById)");
        }

        if (optOldBook.isEmpty()){
            throw new BookNotFoundException();
        }

        if (!optOldBook.get().getIdUser().equals(idUser)){
            throw new UnauthorizedUserAboutBookException();
        }

        var newBook = new Book(
                optOldBook.get().getId(),
                optOldBook.get().getTitle(),
                optOldBook.get().getAuthor(),
                optOldBook.get().getTheme(),
                Status.IN_PROGRESS,
                optOldBook.get().getCoverImage(),
                idUser
        );

        try {
            return repository.save(newBook);
        } catch (Exception e) {
            throw new DataBaseException("(save)");
        }
    }

    public Book updateBookStatusCompleted(UUID idUser, UUID idBook) {
        return null;
    }
}
