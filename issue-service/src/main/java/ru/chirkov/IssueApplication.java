package ru.chirkov;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.chirkov.issueServiceApp.model.Issue;
import ru.chirkov.issueServiceApp.service.IssueService;
import ru.chirkov.issueServiceApp.service.provider.BookProvider;
import ru.chirkov.issueServiceApp.service.provider.ReaderProvider;

import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class IssueApplication {
    public static void main(String[] args) {
        SpringApplication.run(IssueApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    CommandLineRunner commandLineRunner(IssueService issueService,
                                        BookProvider bookProvider,
                                        ReaderProvider readerProvider) {
        return args -> {

            for (int i = 0; i < 20; i++) {
                Issue issue = new Issue();
                Date between = faker().date().between(issueService.startOfYear(), issueService.endOfYear());
                issue.setLocalDate(between.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                issue.setBookId(bookProvider.getRandomBookId());
                issue.setReaderId(readerProvider.getRandomReaderId());
                issueService.createIssue(issue);
            }
        };
    }


}

