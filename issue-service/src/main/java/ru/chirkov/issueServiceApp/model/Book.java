package ru.chirkov.issueServiceApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chirkov.issueServiceApp.service.provider.BookProvider;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public  class Book implements Serializable {
    private Long id;
    private String name;
    private Author author;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Author implements Serializable {
        private Long id;
        private String name;
        private String surname;
        private String patronymic;
    }
}
