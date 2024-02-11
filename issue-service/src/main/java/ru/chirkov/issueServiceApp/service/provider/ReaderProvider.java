package ru.chirkov.issueServiceApp.service.provider;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
public class ReaderProvider {

    private final WebClient webClient;

    public ReaderProvider() {
        webClient = WebClient.builder().build();
    }

    public Long getRandomReaderId() {
        Reader reader = webClient
                .get()
                .uri("http://localhost:8085/api/v1/reader-provider/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
        return reader.getId();

    }

    @Data
    private static class Reader {
        private Long id;
    }
}
