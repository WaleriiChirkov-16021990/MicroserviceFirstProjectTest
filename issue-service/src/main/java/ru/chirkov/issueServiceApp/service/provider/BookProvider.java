package ru.chirkov.issueServiceApp.service.provider;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.*;
import ru.chirkov.annotation.Timer;
import ru.chirkov.issueServiceApp.model.Book;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@Timer
public class BookProvider {
    private final WebClient webClient;
    private final EurekaClient eurekaClient;

    public BookProvider(EurekaClient eurekaClient, ReactorLoadBalancerExchangeFilterFunction lb) {
        this.eurekaClient = eurekaClient;
        this.webClient = WebClient.builder()
                .filter(lb)
                .build();
    }

    public Long getRandomBookId() {
        Book book = webClient
                .get()
                .uri("http://book-service/api/v1/book-provider/random")
//                .uri(getHost() + "/api/v1/book-provider/random")
                .retrieve()
                .bodyToMono(Book.class)
                .block();
        assert book != null;
        return book.getId();
    }

    public Book getBookById(Long id) {
        return webClient
                .get()
                .uri("http://book-service/books/" + id)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

    private String getHost() {
        Application application = eurekaClient.getApplication("book-service");
        if (application == null) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(application.getInstances().size());
        return application.getInstances().get(nextInt).getHomePageUrl();
    }

//    @Data
//    private static class Book implements Serializable {
//        private Long id;
//        private String name;
//        private Author author;
//
//        @Data
//        private static class Author implements Serializable {
//            private Long id;
//            private String name;
//            private String surname;
//            private String patronymic;
//        }
//    }
}
