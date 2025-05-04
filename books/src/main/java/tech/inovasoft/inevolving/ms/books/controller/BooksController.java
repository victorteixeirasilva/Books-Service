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
                        service.updateBookStatusToDo(idUser, idBook)
                )
        );
    }

    @Operation(summary = "Mudar o Status para IN PROGRESS", description = "Retorna o Livro editado.")
    @Async("asyncExecutor")
    @PatchMapping("/status/inprogress/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> updateBookStatusInProgress(@PathVariable UUID idUser, @PathVariable UUID idBook) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.updateBookStatusInProgress(idUser, idBook)
                )
        );
    }

    @Operation(summary = "Mudar o Status para COMPLETED", description = "Retorna o Livro editado.")
    @Async("asyncExecutor")
    @PatchMapping("/status/inprogress/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> updateBookStatusCompleted(@PathVariable UUID idUser, @PathVariable UUID idBook) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.updateBookStatusCompleted(idUser, idBook)
                )
        );
    }
}
