package tech.inovasoft.inevolving.ms.books.domain.dto.request;

public record RequestBookDTO(
        String title,
        String author,
        String theme,
        String coverImage)
{
}
