package tech.inovasoft.inevolving.ms.books.domain.model;

import jakarta.persistence.*;
import lombok.*;
import tech.inovasoft.inevolving.ms.books.domain.dto.request.RequestBookDTO;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String author;
    private String theme;
    private String status = Status.TO_DO;
    private String coverImage;
    private UUID idUser;

    public Book(UUID idUser, RequestBookDTO dto) {
        this.title = dto.title();
        this.author = dto.author();
        this.theme = dto.theme();
        this.coverImage = dto.coverImage();
        this.idUser = idUser;
    }

    public Book(String title, String author, String theme, String status, String coverImage, UUID idUser) {
        this.title = title;
        this.author = author;
        this.theme = theme;
        this.status = status;
        this.coverImage = coverImage;
        this.idUser = idUser;
    }
}
