package ru.chirkov.bookServiceApp.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chirkov.bookServiceApp.dao.BookRepository;
import ru.chirkov.bookServiceApp.model.Author;
import ru.chirkov.bookServiceApp.model.Book;
import ru.chirkov.bookServiceApp.util.exceptionCustom.BadRequestAuthorException;
import ru.chirkov.bookServiceApp.util.exceptionCustom.BadRequestBookException;
import ru.chirkov.bookServiceApp.util.exceptionCustom.NotFoundAuthorCustomeException;
import ru.chirkov.bookServiceApp.util.exceptionCustom.NotFoundBookCustomeException;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = {Exception.class, BadRequestAuthorException.class, BadRequestBookException.class},
        dontRollbackOn = {NotFoundAuthorCustomeException.class, NotFoundBookCustomeException.class})
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    /**
     * Retrieves a list of books.
     *
     * @return the list of books
     */
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the specified ID
     */
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new NotFoundBookCustomeException("Book with id = " + id + " not found."));
    }

    /**
     * Retrieves a list of books by author ID.
     *
     * @param id the ID of the author
     * @return the list of books found
     */
    public List<Book> getBooksByAuthorId(Long id) {
        return bookRepository.findBooksByAuthorId(id);
    }

    /**
     * A method to retrieve a list of books by name.
     *
     * @param name the name of the book to search for
     * @return a list of books with the specified name
     */
    public List<Book> getBooksByName(String name) {
        return bookRepository.findBooksByName(name);
    }

    /**
     * Retrieves a list of books written by a specific author.
     *
     * @param name the name of the author
     * @return the list of books by the author
     */
    public List<Book> getBooksByAuthorName(String name) {
        return bookRepository.findBooksByAuthorName(name);
    }

    /**
     * A method to retrieve a list of books by author's name and surname.
     *
     * @param name    the name of the author
     * @param surname the surname of the author
     * @return a list of books written by the specified author
     */
    public List<Book> getBooksByAuthorNameAndSurname(String name, String surname) {
        return bookRepository.findBooksByAuthorNameAndAuthorSurname(name, surname);
    }

    /**
     * Retrieves a list of books by the author's name, surname, and patronymic.
     *
     * @param name       the author's name
     * @param surname    the author's surname
     * @param patronymic the author's patronymic
     * @return a list of books matching the author's name, surname, and patronymic
     */
    public List<Book> getBooksByAuthorNameAndSurnameAndPatronymic(
            String name, String surname, String patronymic) {
        return bookRepository
                .findBooksByAuthorNameAndAuthorSurnameAndAuthorPatronymic(name, surname, patronymic)
                .orElseThrow(() -> {
                            return new NotFoundBookCustomeException("Books with Author name = " + name
                                    + " and surname = " + surname
                                    + " and patronymic = " + patronymic
                                    + " not found.");
                        }
                );
    }

    /**
     * Retrieves a list of books by the author's name, surname, patronymic, and book name.
     *
     * @param name       the author's name
     * @param surname    the author's surname
     * @param patronymic the author's patronymic
     * @param nameBook   the name of the book
     * @return a list of books matching the given criteria
     */
    public List<Book> getBooksByAuthorNameAndSurnameAndPatronymicBooksName(
            String name, String surname, String patronymic, String nameBook) {
        return bookRepository
                .findByAuthor_NameStartsWithIgnoreCaseAndAuthor_SurnameStartsWithIgnoreCaseAndAuthor_PatronymicLikeIgnoreCaseAndAuthor_Books_NameContainsIgnoreCase(name, surname, patronymic, nameBook)
                .orElseThrow(() -> {
                    return new NotFoundAuthorCustomeException("Author with name = " + name
                            + " and surname = " + surname
                            + " and patronymic = " + patronymic
                            + " not found.");
                });
    }

    /**
     * Retrieves a book by name.
     *
     * @param name the name of the book to retrieve
     * @return the book with the specified name
     */
    public Book getBookByName(String name) {
        return bookRepository
                .findBookByName(name)
                .orElseThrow(() -> new NotFoundBookCustomeException("Book with name = " + name + " not found."));
    }

    /**
     * Saves a book.
     *
     * @param book the book to be saved
     * @return the saved book
     */
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Delete a book from the repository.
     *
     * @param book the book to be deleted
     * @return void
     */
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to be deleted
     * @return void
     */
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Updates a book with the given ID.
     *
     * @param id   the ID of the book to update
     * @param book the updated book object
     * @return the updated book object
     */
    public Book updateBook(Long id, Book book) {
        book.setId(id);
        return bookRepository.save(book);
    }

    /**
     * A function to update the name of a book by its ID.
     *
     * @param id   the ID of the book
     * @param name the new name of the book
     * @return the updated book
     */
    public Book patchNameBookById(Long id, String name) {
        Book book = getBookById(id);
        book.setName(name);
        return bookRepository.save(book);
    }

    /**
     * A description of the entire Java function.
     *
     * @param id       description of parameter
     * @param authorId description of parameter
     * @return description of return value
     */
    public Book patchAuthorBookById(Long id, Long authorId) {
        Book book = getBookById(id);
        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

}
