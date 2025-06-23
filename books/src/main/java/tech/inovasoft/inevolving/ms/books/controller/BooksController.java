package tech.inovasoft.inevolving.ms.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.dto.response.ResponseDeleteBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.BookNotFoundException;
import tech.inovasoft.inevolving.ms.books.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.exception.UnauthorizedUserAboutBookException;
import tech.inovasoft.inevolving.ms.books.domain.model.Book;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;
import tech.inovasoft.inevolving.ms.books.service.BooksService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Books", description = "Gerenciador dos end-poits do serviço de Leitura | Reading service endpoint manager")
@RestController
@RequestMapping("/ms/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @Operation(summary = "Adicionar um novo Livro na lista do Usuário. | Add a new Book to the User's list.", description = "Retorna o Livro cadastrado. | Returns the registered Book.")
    @Async("asyncExecutor")
    @PostMapping("/{idUser}")
    public CompletableFuture<ResponseEntity<Book>> addBook(
            @PathVariable UUID idUser,
            @RequestBody RequestBookDTO dto
    ) throws NotSavedDTOInDbException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.addBook(idUser, dto)
        ));
    }

    @Operation(summary = "Editar Livro. | Edit Book.", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity<Book>> updateBook(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook,
            @RequestBody RequestBookDTO dto
    ) throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.updateBook(idUser, idBook, dto)
        ));
    }

    @Operation(summary = "Mudar o Status para TODO | Change Status TODO", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/status/todo/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity<Book>> updateBookStatusToDo(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.updateBookStatus(idUser, idBook, Status.TO_DO)
        ));
    }

    @Operation(summary = "Mudar o Status para IN PROGRESS | Change Status to IN PROGRESS", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/status/progress/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity<Book>> updateBookStatusInProgress(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.updateBookStatus(idUser, idBook, Status.IN_PROGRESS)
        ));
    }

    @Operation(summary = "Mudar o Status para COMPLETED | Change Status to COMPLETED", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/status/completed/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity<Book>> updateBookStatusCompleted(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.updateBookStatus(idUser, idBook, Status.COMPLETED)
        ));
    }

    @Operation(summary = "Deletar Livro. | Delete Book.", description = "Retorna confirmação que o Livro foi deletado. | Returns confirmation that the Book has been deleted.")
    @Async("asyncExecutor")
    @DeleteMapping("/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity<ResponseDeleteBookDTO>> deleteBook(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.deleteBook(idUser, idBook)
        ));
    }

    @Operation(summary = "Ver todos os Livros de um Usuário.", description = "Retorna uma lista com os Livros cadastrados.")
    @Async("asyncExecutor")
    @GetMapping("/{idUser}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooks(
            @PathVariable UUID idUser
    ) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.getBooks(idUser)
        ));
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status TO DO.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/todo/{idUser}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooksToDo(
            @PathVariable UUID idUser
    ) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.getBooksStatus(idUser, Status.TO_DO)
        ));
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status IN PROGRESS.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/progress/{idUser}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooksInProgress(
            @PathVariable UUID idUser
    ) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.getBooksStatus(idUser, Status.IN_PROGRESS)
        ));
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status COMPLETED.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/completed/{idUser}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooksCompleted(
            @PathVariable UUID idUser
    ) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.getBooksStatus(idUser, Status.COMPLETED)
        ));
    }

    @Operation(summary = "Pegar Livro.", description = "Retorna o livro cadastrado.")
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity<Book>> getBook(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(ResponseEntity.ok(
                service.getBook(idUser, idBook)
        ));
    }
}
