package ru.chirkov.issueServiceApp.service.provider;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class ReaderProvider {

    private final WebClient webClient;
    private final EurekaClient eurekaClient;

    public ReaderProvider(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
        webClient = WebClient.builder().build();
    }

    public Long getRandomReaderId() {
        Reader reader = webClient
                .get()
                .uri(getHost() + "/api/v1/reader-provider/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
        return reader.getId();

    }

    private String getHost() {
        Application application = eurekaClient.getApplication("reader-service");
        if (application == null) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(application.getInstances().size());
        return application.getInstances().get(nextInt).getHomePageUrl();
    }

    @Data
    private static class Reader {
        private Long id;
    }
}
