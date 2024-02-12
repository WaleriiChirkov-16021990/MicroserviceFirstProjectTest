package ru.chirkov.issueServiceApp.service.provider;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.*;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class BookProvider {
    private final WebClient webClient;
    private final EurekaClient eurekaClient;

    public BookProvider(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
        this.webClient = WebClient.builder().build();
    }

    public Long getRandomBookId() {
        Book book = webClient
                .get()
                .uri(getHost() + "/api/v1/book-provider/random")
                .retrieve()
                .bodyToMono(Book.class)
                .block();
        return book.getId();
    }

    private String getHost() {
        Application application = eurekaClient.getApplication("book-service");
        if (application == null) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(application.getInstances().size());
        return application.getInstances().get(nextInt).getHomePageUrl();
    }

    @Data
    private static class Book {
        private Long id;
    }
}
