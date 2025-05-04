package tech.inovasoft.inevolving.ms.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
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

    public Book updateBook(UUID idUser, UUID idBook, RequestBookDTO dto) {
        Optional<Book> optOldBook = Optional.empty();
        try {
            optOldBook = repository.findById(idBook);
        } catch (Exception e) {
            // TODO erro de integração com DB.
            return null;
        }

        if (optOldBook.isEmpty()){
            // TODO erro de livro não encontrado.
        }

        if (!optOldBook.get().getIdUser().equals(idUser)){
            // TODO erro de autorização do usuário sobre o livro
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
            // TODO erro de integração com DB.
            return null;
        }
    }
}
