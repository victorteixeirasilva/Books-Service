package tech.inovasoft.inevolving.ms.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;

import java.util.UUID;

public interface BooksRepository extends JpaRepository<Book, UUID> {

}
