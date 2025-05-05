package tech.inovasoft.inevolving.ms.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.dto.response.ResponseDeleteBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.BookNotFoundException;
import tech.inovasoft.inevolving.ms.books.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.exception.UnauthorizedUserAboutBookException;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.repository.BooksRepository;

import java.util.List;
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

    public Book updateBookStatus(UUID idUser, UUID idBook, String status) throws DataBaseException, BookNotFoundException, UnauthorizedUserAboutBookException {
        Optional<Book> optOldBook;
        try {
            optOldBook = repository.findById(idBook);
        } catch (Exception e) {
            //TODO falta teste
            throw new DataBaseException("(findById)");
        }

        if (optOldBook.isEmpty()){
            //TODO falta teste
            throw new BookNotFoundException();
        }

        if (!optOldBook.get().getIdUser().equals(idUser)){
            //TODO falta teste
            throw new UnauthorizedUserAboutBookException();
        }

        var newBook = new Book(
                optOldBook.get().getId(),
                optOldBook.get().getTitle(),
                optOldBook.get().getAuthor(),
                optOldBook.get().getTheme(),
                status,
                optOldBook.get().getCoverImage(),
                idUser
        );

        try {
            return repository.save(newBook);
        } catch (Exception e) {
            //TODO falta teste
            throw new DataBaseException("(save)");
        }
    }

    public ResponseDeleteBookDTO deleteBook(UUID idUser, UUID idBook) throws DataBaseException, BookNotFoundException, UnauthorizedUserAboutBookException {
        Optional<Book> optOldBook;
        try {
            optOldBook = repository.findById(idBook);
        } catch (Exception e) {
            //TODO falta teste
            throw new DataBaseException("(findById)");
        }

        if (optOldBook.isEmpty()){
            //TODO falta teste
            throw new BookNotFoundException();
        }

        if (!optOldBook.get().getIdUser().equals(idUser)){
            //TODO falta teste
            throw new UnauthorizedUserAboutBookException();
        }

        try {
            repository.delete(optOldBook.get());
            return new ResponseDeleteBookDTO("Livro deletado.");
        } catch (Exception e) {
            //TODO falta teste
            throw new DataBaseException("(delete)");
        }
    }

    public List<Book> getBooks(UUID idUser) throws DataBaseException, BookNotFoundException {
        List<Book> bookList;
        try {
            bookList = repository.findAllByUserId(idUser);
        } catch (Exception e) {
            //TODO falta teste
            throw new DataBaseException("(findAllByUserId)");
        }

        if (bookList.isEmpty()){
            //TODO falta teste
            throw new BookNotFoundException("User does not have any registered books.");
        }

        return bookList;
    }

    public List<Book> getBooksStatus(UUID idUser, String status) throws DataBaseException, BookNotFoundException {
        return null;
    }
}
