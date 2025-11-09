package tech.inovasoft.inevolving.ms.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import tech.inovasoft.inevolving.ms.books.service.TokenService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Books", description = "Gerenciador dos end-poits do serviço de Leitura | Reading service endpoint manager")
@RestController
@RequestMapping("/ms/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Adicionar um novo Livro na lista do Usuário. | Add a new Book to the User's list.", description = "Retorna o Livro cadastrado. | Returns the registered Book.")
    @Async("asyncExecutor")
    @PostMapping("/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<Book>> addBook(
            @PathVariable UUID idUser,
            @RequestBody RequestBookDTO dto,
            @PathVariable String token
    ) throws NotSavedDTOInDbException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.addBook(idUser, dto)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Editar Livro. | Edit Book.", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/{idUser}/{idBook}/{token}")
    public CompletableFuture<ResponseEntity<Book>> updateBook(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook,
            @RequestBody RequestBookDTO dto,
            @PathVariable String token
    ) throws UnauthorizedUserAboutBookException, BookNotFoundException, DataBaseException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.updateBook(idUser, idBook, dto)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Mudar o Status para TODO | Change Status TODO", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/status/todo/{idUser}/{idBook}/{token}")
    public CompletableFuture<ResponseEntity<Book>> updateBookStatusToDo(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.updateBookStatus(idUser, idBook, Status.TO_DO)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Mudar o Status para IN PROGRESS | Change Status to IN PROGRESS", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/status/progress/{idUser}/{idBook}/{token}")
    public CompletableFuture<ResponseEntity<Book>> updateBookStatusInProgress(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.updateBookStatus(idUser, idBook, Status.IN_PROGRESS)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Mudar o Status para COMPLETED | Change Status to COMPLETED", description = "Retorna o Livro editado. | Returns the edited Book.")
    @Async("asyncExecutor")
    @PutMapping("/status/completed/{idUser}/{idBook}/{token}")
    public CompletableFuture<ResponseEntity<Book>> updateBookStatusCompleted(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.updateBookStatus(idUser, idBook, Status.COMPLETED)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Deletar Livro. | Delete Book.", description = "Retorna confirmação que o Livro foi deletado. | Returns confirmation that the Book has been deleted.")
    @Async("asyncExecutor")
    @DeleteMapping("/{idUser}/{idBook}/{token}")
    public CompletableFuture<ResponseEntity<ResponseDeleteBookDTO>> deleteBook(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.deleteBook(idUser, idBook)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Ver todos os Livros de um Usuário.", description = "Retorna uma lista com os Livros cadastrados.")
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooks(
            @PathVariable UUID idUser,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.getBooks(idUser)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status TO DO.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/todo/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooksToDo(
            @PathVariable UUID idUser,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.getBooksStatus(idUser, Status.TO_DO)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status IN PROGRESS.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/progress/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooksInProgress(
            @PathVariable UUID idUser,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.getBooksStatus(idUser, Status.IN_PROGRESS)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Ver todos os Livros de um Usuário, com status COMPLETED.", description = "Retorna uma lista com os Livros cadastrados com status TODO.")
    @Async("asyncExecutor")
    @GetMapping("/status/completed/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBooksCompleted(
            @PathVariable UUID idUser,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException {
        if (tokenIsValid(token)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.getBooksStatus(idUser, Status.COMPLETED)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    @Operation(summary = "Pegar Livro.", description = "Retorna o livro cadastrado.")
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{idBook}/{token}")
    public CompletableFuture<ResponseEntity<Book>> getBook(
            @PathVariable UUID idUser,
            @PathVariable UUID idBook,
            @PathVariable String token
    ) throws BookNotFoundException, DataBaseException, UnauthorizedUserAboutBookException {
        if (tokenIsValid(token)){
            return CompletableFuture.completedFuture(ResponseEntity.ok(
                    service.getBook(idUser, idBook)
            ));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

    private boolean tokenIsValid(String token) {
        try {
            var validateToken = tokenService.validateToken(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
