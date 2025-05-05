package tech.inovasoft.inevolving.ms.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.BookNotFoundException;
import tech.inovasoft.inevolving.ms.books.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
import tech.inovasoft.inevolving.ms.books.domain.exception.UnauthorizedUserAboutBookException;
import tech.inovasoft.inevolving.ms.books.domain.model.Status;
import tech.inovasoft.inevolving.ms.books.service.BooksService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Books", description = "Gerenciador dos end-poits do serviço de Leitura")
@RestController
@RequestMapping("/ms/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @Operation(summary = "Adiciona um novo Livro na lista do Usuário.", description = "Retorna o Livro cadastrado.")
    @Async("asyncExecutor")
    @PostMapping("/{idUser}")
    public CompletableFuture<ResponseEntity> addBook(@PathVariable UUID idUser, @RequestBody RequestBookDTO dto) throws NotSavedDTOInDbException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.addBook(idUser, dto)
                )
        );
    }

    @Operation(summary = "Editar Livro.", description = "Retorna o Livro editado.")
    @Async("asyncExecutor")
    @PatchMapping("/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> updateBook(@PathVariable UUID idUser, @PathVariable UUID idBook, @RequestBody RequestBookDTO dto) throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.updateBook(idUser, idBook, dto)
                )
        );
    }

    @Operation(summary = "Mudar o Status para TODO", description = "Retorna o Livro editado.")
    @Async("asyncExecutor")
    @PatchMapping("/status/todo/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> updateBookStatusToDo(@PathVariable UUID idUser, @PathVariable UUID idBook) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.updateBookStatus(idUser, idBook, Status.TO_DO)
                )
        );
    }

    @Operation(summary = "Mudar o Status para IN PROGRESS", description = "Retorna o Livro editado.")
    @Async("asyncExecutor")
    @PatchMapping("/status/progress/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> updateBookStatusInProgress(@PathVariable UUID idUser, @PathVariable UUID idBook) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.updateBookStatus(idUser, idBook, Status.IN_PROGRESS)
                )
        );
    }

    @Operation(summary = "Mudar o Status para COMPLETED", description = "Retorna o Livro editado.")
    @Async("asyncExecutor")
    @PatchMapping("/status/completed/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> updateBookStatusCompleted(@PathVariable UUID idUser, @PathVariable UUID idBook) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.updateBookStatus(idUser, idBook, Status.COMPLETED)
                )
        );
    }

    @Operation(summary = "Deletar Livro.", description = "Retorna confirmação que o Livro foi deletado.")
    @Async("asyncExecutor")
    @DeleteMapping("/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> deleteBook(@PathVariable UUID idUser, @PathVariable UUID idBook) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.deleteBook(idUser, idBook)
                )
        );
    }

    @Operation(summary = "Ver todos os Livros de um Usuário.", description = "Retorna uma lista com os Livros cadastrados.")
    @Async("asyncExecutor")
    @GetMapping("/{idUser}")
    public CompletableFuture<ResponseEntity> getBooks(@PathVariable UUID idUser) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.getBooks(idUser)
                )
        );
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status TO DO.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/todo/{idUser}")
    public CompletableFuture<ResponseEntity> getBooksToDo(@PathVariable UUID idUser) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.getBooksStatus(idUser, Status.TO_DO)
                )
        );
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status IN PROGRESS.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/progress/{idUser}")
    public CompletableFuture<ResponseEntity> getBooksInProgress(@PathVariable UUID idUser) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.getBooksStatus(idUser, Status.IN_PROGRESS)
                )
        );
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status COMPLETED.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/completed/{idUser}")
    public CompletableFuture<ResponseEntity> getBooksCompleted(@PathVariable UUID idUser) throws BookNotFoundException, DataBaseException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.getBooksStatus(idUser, Status.COMPLETED)
                )
        );
    }

    @Operation(summary = "Pegar Livro.", description = "Retorna o livro cadastrado.")
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> getBook(@PathVariable UUID idUser, @PathVariable UUID idBook) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.getBook(idUser, idBook)
                )
        );
    }
}
