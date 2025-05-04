package tech.inovasoft.inevolving.ms.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.BookNotFoundException;
import tech.inovasoft.inevolving.ms.books.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.exception.UnauthorizedUserAboutBook;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
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

    public Book updateBook(UUID idUser, UUID idBook, RequestBookDTO dto) throws DataBaseException, BookNotFoundException, UnauthorizedUserAboutBook {
        Optional<Book> optOldBook = Optional.empty();
        try {
            optOldBook = repository.findById(idBook);
        } catch (Exception e) {
            throw new DataBaseException();
        }

        if (optOldBook.isEmpty()){
            throw new BookNotFoundException();
        }

        if (!optOldBook.get().getIdUser().equals(idUser)){
            throw new UnauthorizedUserAboutBook();
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
            throw new DataBaseException();
        }
    }


}
