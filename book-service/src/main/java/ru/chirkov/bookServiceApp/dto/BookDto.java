package ru.chirkov.bookServiceApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chirkov.bookServiceApp.model.Author;
import ru.chirkov.bookServiceApp.model.Book;

import java.io.Serializable;

/**
 * DTO for {@link Book}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto implements Serializable {

    @Min(value = 0, message = "Id should be greater than 0")
    private Long id;
    @NotNull(message = "Name should be not null")
    @Size(message = "Name should be length between 3 do 100 symbol", min = 3, max = 100)
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @NotNull(message = "Author should be not null")
    private BookDto.AuthorDto author;

    /**
     * DTO for {@link Author}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthorDto implements Serializable {
        @Min(value = 0, message = "Id should be greater than 0")
        private Long id;
        @NotNull(message = "Name should be not null")
        @Size(message = "Name should be length between 3 do 100 symbol", min = 3, max = 100)
        @NotEmpty(message = "Name should be not empty")
        private String name;
        @NotNull(message = "Surname should be not null")
        @Size(message = "Surname should be length between 3 do 100 symbol", min = 3, max = 100)
        @NotEmpty(message = "Surname should be not empty")
        private String surname;
        private String patronymic;
    }
}