package ru.chirkov.issueServiceApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chirkov.issueServiceApp.model.Book;
import ru.chirkov.issueServiceApp.model.Reader;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class IssueDtoFull {

    private Long issue;
    private LocalDate startDate;
    private Reader reader;
    private Book book;
}
