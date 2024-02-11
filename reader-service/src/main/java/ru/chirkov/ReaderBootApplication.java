package ru.chirkov;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.chirkov.readerServiceApp.model.Reader;
import ru.chirkov.readerServiceApp.service.ReaderService;

@SpringBootApplication
public class ReaderBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReaderBootApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Faker getFaker() {
        return new Faker();
    }
    @Bean
    CommandLineRunner commandLineRunner(ReaderService readerService, Faker faker) {
        return args -> {
            for (int i = 0; i < 30; i++) {
                Reader reader = new Reader();
                reader.setName(faker.name().firstName());
                reader.setSurname(faker.name().lastName());
                readerService.saveReader(reader);
            }
        };
    }
}