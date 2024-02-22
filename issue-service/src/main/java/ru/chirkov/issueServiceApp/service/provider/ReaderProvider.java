package ru.chirkov.issueServiceApp.service.provider;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.chirkov.annotation.Timer;
import ru.chirkov.issueServiceApp.model.Reader;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@Timer
public class ReaderProvider {

    private final WebClient webClient;
    private final EurekaClient eurekaClient;

    public ReaderProvider(EurekaClient eurekaClient, ReactorLoadBalancerExchangeFilterFunction lb) {
        this.eurekaClient = eurekaClient;
        webClient = WebClient.builder()
                .filter(lb)
                .build();
    }

    public Long getRandomReaderId() {
        Reader reader = webClient
                .get()
                .uri("http://reader-service/api/v1/reader-provider/random")
//                .uri(getHost() + "/api/v1/reader-provider/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
        return reader.getId();
    }


    public Reader getReaderById(Long id) {
        return webClient
                .get()
                .uri("http://reader-service/readers/" + id)
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
    }

    private String getHost() {
        Application application = eurekaClient.getApplication("reader-service");
        if (application == null) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(application.getInstances().size());
        return application.getInstances().get(nextInt).getHomePageUrl();
    }

//    @Data
//    private static class Reader {
//        private Long id;
//        private String name;
//        private String surname;
//    }
}
