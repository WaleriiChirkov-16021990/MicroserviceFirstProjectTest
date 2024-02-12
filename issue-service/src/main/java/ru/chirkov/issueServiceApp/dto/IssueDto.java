package ru.chirkov.issueServiceApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chirkov.issueServiceApp.model.Issue;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Issue}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueDto implements Serializable {
    @NotNull
    @Min(0)
    @Max(value = 9223372036854775807L, message = "The number of id must be less than or equal to 9223372036854775807")
    @PositiveOrZero
    private Long id;
    @NotNull
    @PastOrPresent
    private LocalDate localDate;
    @NotNull
    @Min(0)
    @Max(value = 9223372036854775807L, message = "readerId must be less than or equal to 9223372036854775807")
    private Long readerId;
    @NotNull
    @Min(0)
    @Max(value = 9223372036854775807L, message = "bookId must be less than or equal to 9223372036854775807")
    private Long bookId;
}

