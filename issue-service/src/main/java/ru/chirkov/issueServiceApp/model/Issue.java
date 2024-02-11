package ru.chirkov.issueServiceApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "issue")
public class Issue implements Serializable {
    @Serial
    private static final long serialVersionUID = 7771028347191988404L;
    private Long id;
    private LocalDate localDate;
    private Long readerId;
    private Long bookId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NotNull
    @Min(value = 0, message = "The id must be greater than or equal to 0")
    @Max(value = 9223372036854775807L, message = "bookId must be less than or equal to 9223372036854775807")
    public Long getId() {
        return id;
    }

    @Column(name = "local_date", nullable = false)
    @PastOrPresent(message = "The date must be in the past or present")
    public LocalDate getLocalDate() {
        return localDate;
    }

    @Column(name = "reader_id", nullable = false)
    @NotNull
    @Min(value = 0, message = "The reader id must be greater than or equal to 0")
    @Max(value = 9223372036854775807L, message = "bookId must be less than or equal to 9223372036854775807")
    public Long getReaderId() {
        return readerId;
    }

    @Column(name = "book_id", nullable = false)
    @NotNull
    @Min(value = 0, message = "The book id must be greater than or equal to 0")
    @Max(value = 9223372036854775807L, message = "bookId must be less than or equal to 9223372036854775807")
    public Long getBookId() {
        return bookId;
    }

}