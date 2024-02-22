package ru.chirkov;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.chirkov.annotation.Timer;
import ru.chirkov.bookServiceApp.model.Author;
import ru.chirkov.bookServiceApp.model.Book;
import ru.chirkov.bookServiceApp.service.AuthorService;
import ru.chirkov.bookServiceApp.service.BookService;

import java.util.List;

@SpringBootApplication
public class BookServiceApplication {
    /**
     * A description of the entire Java function.
     *
     * @param args an array of command-line arguments for the application
     * @return void
     */
    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);

    }

    /**
     * A description of the entire Java function.
     *
     * @return description of return value
     */
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
    /**
     * A description of the entire Java function.
     *
     * @return         	description of return value
     */
    @Bean
    Faker getFaker() {
        return new Faker();
    }

    /**
     * A CommandLineRunner function that generates and saves 30 random books with authors using the provided bookService, authorService, and Faker.
     *
     * @param bookService   the service for managing books
     * @param authorService the service for managing authors
     * @param faker         a library for generating fake data
     * @return a command line runner that generates and saves random books
     */
    @Bean
    @Timer
    CommandLineRunner commandLineRunner2(BookService bookService, AuthorService authorService, Faker faker) {
        return args -> {
            for (int i = 0; i < 30; i++) {
                Book book = new Book();
                book.setName(faker.book().title());
                Author author = new Author();
                author.setName(faker.name().firstName());
                author.setSurname(faker.name().lastName());
                author.setPatronymic(faker.name().firstName());
                book.setAuthor(author);
                bookService.saveBook(book);
            }
        };
    }
}