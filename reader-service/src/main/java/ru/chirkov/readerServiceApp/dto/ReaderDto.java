package ru.chirkov.readerServiceApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link ru.chirkov.readerServiceApp.model.Reader}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReaderDto implements Serializable {
    @NotNull(message = "Id should be not null")
    @Min(message = "Id should be range from 0 to Long.MAX_VALUE", value = 0)
    private Long id;
    @NotNull
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100")
    @NotEmpty
    private String name;

    @Size(min = 3, max = 100, message = "Surname should be between 3 and 100")
    private String surname;
}