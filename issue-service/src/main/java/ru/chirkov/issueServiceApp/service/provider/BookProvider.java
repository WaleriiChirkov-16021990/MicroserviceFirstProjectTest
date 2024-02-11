package ru.chirkov.issueServiceApp.service.provider;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.*;

@Service
@Transactional
public class BookProvider {
    private final WebClient webClient;

    public BookProvider() {
        this.webClient = WebClient.builder().build();
    }

    public Long getRandomBookId() {
        Book book = webClient
                .get()
                .uri("http://localhost:8082/api/v1/book-provider/random")
                .retrieve()
                .bodyToMono(Book.class)
                .block();
        return book.getId();
    }

    @Data
    private static class Book {
        private Long id;
    }
}
