package tech.inovasoft.inevolving.ms.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;

import java.util.List;
import java.util.UUID;

public interface BooksRepository extends JpaRepository<Book, UUID> {

    @Query("SELECT b FROM Book b WHERE b.idUser = :idUser")
    List<Book> findAllByUserId(@Param("idUser") UUID idUser);


    @Query("SELECT b FROM Book b WHERE b.idUser = :idUser AND b.status = :bookStatus")
    List<Book> findAllByUserIdAndStatus(@Param("idUser") UUID idUser,@Param("bookStatus") String bookStatus);

}
