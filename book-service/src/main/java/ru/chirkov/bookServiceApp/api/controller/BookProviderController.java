package ru.chirkov.bookServiceApp.api.controller;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chirkov.annotation.Timer;
import ru.chirkov.bookServiceApp.model.Book;
import ru.chirkov.bookServiceApp.service.BookService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/book-provider")
@Timer
public class BookProviderController {
    private final BookService bookService;
    private final Faker faker;

    @GetMapping("/random")
    public ResponseEntity<Book> getBooksRandom() {
        List<Book> books = bookService.getBooks();
        return ResponseEntity.ok(books.get(faker.number().numberBetween(0, books.size() - 1)));
    }
}
