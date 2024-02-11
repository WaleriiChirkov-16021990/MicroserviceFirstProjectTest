package ru.chirkov.bookServiceApp.api.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chirkov.bookServiceApp.dto.BookDto;
import ru.chirkov.bookServiceApp.model.Book;
import ru.chirkov.bookServiceApp.service.AuthorService;
import ru.chirkov.bookServiceApp.service.BookService;
import ru.chirkov.bookServiceApp.util.exceptionCustom.CustomValidationException;
import ru.chirkov.bookServiceApp.util.exceptionCustom.NotFoundAuthorCustomeException;
import ru.chirkov.bookServiceApp.util.exceptionCustom.NotFoundBookCustomeException;
import ru.chirkov.bookServiceApp.util.response.AddErrorMessage;
import ru.chirkov.bookServiceApp.util.response.ResponseByException;
import ru.chirkov.bookServiceApp.util.validationCustom.BookCustomeValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final AuthorService authorService;

    private final BookCustomeValidator customeValidator;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper, AuthorService authorService, BookCustomeValidator validator) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.customeValidator = validator;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks()
                .stream().map((element) -> modelMapper.map(modelMapper.map(element.getAuthor(), BookDto.AuthorDto.class), BookDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(
                modelMapper.map(bookService.getBookById(id).getAuthor(), BookDto.AuthorDto.class)
                , BookDto.class));
    }

    @GetMapping(value = "/author/{id}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBooksByAuthorId(id)
                .stream()
                .map((element) -> modelMapper.map(
                        modelMapper.map(element.getAuthor(), BookDto.AuthorDto.class)
                        , BookDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/search/{name}")
    public ResponseEntity<List<BookDto>> getBooksByName(@PathVariable String name) {
        return ResponseEntity.ok(bookService.getBooksByName(name)
                .stream()
                .map((element) -> modelMapper.map(
                        modelMapper.map(element.getAuthor(), BookDto.AuthorDto.class)
                        , BookDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/search/author/{name}")
    public ResponseEntity<List<BookDto>> getBooksByAuthorName(@PathVariable String name) {
        return ResponseEntity.ok(bookService.getBooksByAuthorName(name)
                .stream()
                .map((element) -> modelMapper.map(
                        modelMapper.map(element.getAuthor(), BookDto.AuthorDto.class)
                        , BookDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/search/author/{name}/surname/{surname}")
    public ResponseEntity<List<BookDto>> getBooksByAuthorNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return ResponseEntity.ok(bookService.getBooksByAuthorNameAndSurname(name, surname)
                .stream()
                .map((element) -> modelMapper.map(
                        modelMapper.map(element.getAuthor(), BookDto.AuthorDto.class), BookDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/search/author/{name}/surname/{surname}/patronymic/{patronymic}")
    public ResponseEntity<List<BookDto>> getBooksByAuthorNameAndSurnameAndPatronymic(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable String patronymic) {
        return ResponseEntity.ok(bookService.getBooksByAuthorNameAndSurnameAndPatronymic(name, surname, patronymic)
                .stream().map((element) -> modelMapper.map(element, BookDto.class))
                .collect(Collectors.toList()));
    }


    @GetMapping(value = "/search/author/{name}/surname/{surname}/patronymic/{patronymic}/books/{bookName}")
    public ResponseEntity<List<BookDto>> getBooksByAuthorNameAndSurnameAndPatronymicBooksName(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable String patronymic,
            @PathVariable String bookName) {
        return ResponseEntity.ok(
                bookService.getBooksByAuthorNameAndSurnameAndPatronymicBooksName(name, surname, patronymic, bookName)
                        .stream().map((element) -> modelMapper.map(element, BookDto.class))
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto bookDto, BindingResult result) {
        Book book = modelMapper.map(bookDto, Book.class);
        customeValidator.validate(book, result);
        if (result.hasErrors()) {
            throw new CustomValidationException(
                    AddErrorMessage.addErrorMessageFromBindingResult(result));
        }
        return ResponseEntity.ok(modelMapper.map(bookService.saveBook(book), BookDto.class));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@RequestBody Book book) {
        bookService.deleteBook(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody @Valid BookDto bookDto
            , BindingResult result) {
        Book book = modelMapper.map(bookDto, Book.class);
        book.setId(id);
        customeValidator.validate(book, result);
        if (result.hasErrors()) {
            throw new CustomValidationException(
                    AddErrorMessage.addErrorMessageFromBindingResult(result));
        }
        bookService.updateBook(id, book);
        return ResponseEntity.ok(modelMapper.map(book, BookDto.class));
    }

    @PatchMapping(value = "/{id}/{name}")
    public ResponseEntity<BookDto> patchBook(@PathVariable Long id, @PathVariable String name
            , BindingResult result) {
        Book bookPatching = bookService.getBookById(id);
        bookPatching.setName(name);
        customeValidator.validate(bookPatching, result);
        if (result.hasErrors()) {
            throw new CustomValidationException(
                    AddErrorMessage.addErrorMessageFromBindingResult(result));
        }
        return ResponseEntity.ok(modelMapper.map(bookService.patchNameBookById(id, name), BookDto.class));
    }

    @PatchMapping(value = "/{id}/{authorId}/")
    public ResponseEntity<BookDto> patchBook(@PathVariable Long id, @PathVariable Long authorId
            , BindingResult result) {
        Book bookPatching = bookService.getBookById(id);
        bookPatching.setAuthor(authorService.getAuthorById(authorId));
        customeValidator.validate(bookPatching, result);
        if (result.hasErrors()) {
            throw new CustomValidationException(
                    AddErrorMessage.addErrorMessageFromBindingResult(result));
        }
        return ResponseEntity.ok(modelMapper.map(bookService.patchAuthorBookById(id, authorId), BookDto.class));
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ResponseByException> handleValidationException(CustomValidationException e) {
        return new ResponseEntity<>(new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundBookCustomeException.class)
    public ResponseEntity<ResponseByException> handleNotFoundException(NotFoundBookCustomeException e) {
        return new ResponseEntity<>(new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName())
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundAuthorCustomeException.class)
    public ResponseEntity<ResponseByException> handleNotFoundException(NotFoundAuthorCustomeException e) {
        return new ResponseEntity<>(new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName())
                , HttpStatus.NOT_FOUND);
    }
}
