package ru.chirkov.bookServiceApp.util.validationCustom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chirkov.bookServiceApp.model.Book;
import ru.chirkov.bookServiceApp.service.BookService;

@Component
public class BookCustomeValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookCustomeValidator(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Check if the class is supported.
     *
     * @param clazz The class to check support for
     * @return true if the class is supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Book.class);
    }

    /**
     * Validates the given object and errors.
     *
     * @param target the object to validate
     * @param errors the errors to record validation failures
     */
    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookService.getBookByName(book.getName()) != null) {
            Book loadedBook = bookService.getBookByName(book.getName());
            if (loadedBook.getAuthor().equals(book.getAuthor())) {
                errors.rejectValue("name", "", "Book with this name already exists");
            }
        }
    }
}
