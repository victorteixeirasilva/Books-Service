package tech.inovasoft.inevolving.ms.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;
import tech.inovasoft.inevolving.ms.books.domain.exception.NotSavedDTOInDbException;
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
    @PostMapping("/{idUser}/{idBook}")
    public CompletableFuture<ResponseEntity> updateBook(@PathVariable UUID idUser, @PathVariable UUID idBook, @RequestBody RequestBookDTO dto) {
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(
                        service.updateBook(idUser, idBook, dto)
                )
        );
    }

}
