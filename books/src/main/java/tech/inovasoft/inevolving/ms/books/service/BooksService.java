package tech.inovasoft.inevolving.ms.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.repository.BooksRepository;

import java.util.UUID;

@Service
public class BooksService {

    @Autowired
    private BooksRepository repository;


    public Book addBook(UUID idUser, RequestBookDTO dto) throws NotSavedDTOInDbException {
        try {
            return repository.save(new Book(idUser, dto));
        } catch (Exception e) {
            //TODO falta teste
            throw new NotSavedDTOInDbException();
        }
    }
}
