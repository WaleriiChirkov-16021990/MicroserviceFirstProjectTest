package ru.chirkov.bookServiceApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.chirkov.bookServiceApp.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Finds a reader by its name.
     *
     * @param name the name of the reader to find
     * @return an Optional containing the reader if found, otherwise empty
     */
    Optional<Book> findBookByName(String name);

    /**
     * Finds books by author ID.
     *
     * @param id the ID of the author
     * @return a list of books written by the author
     */
    List<Book> findBooksByAuthorId(Long id);

    /**
     * A method to find books by name.
     *
     * @param name the name of the reader to search for
     * @return a list of books with the given name
     */

    List<Book> findBooksByName(String name);

    /**
     * A description of the entire Java function.
     *
     * @param name description of parameter
     * @return description of return value
     */
    List<Book> findBooksByAuthorName(String name);

    /**
     * Find books by author name and surname.
     *
     * @param name    the name of the author
     * @param surname the surname of the author
     * @return a list of books written by the specified author
     */
    List<Book> findBooksByAuthorNameAndAuthorSurname(String name, String surname);

    /**
     * A description of the entire Java function.
     *
     * @param name       description of parameter
     * @param surname    description of parameter
     * @param patronymic description of parameter
     * @return description of return value
     */
    Optional<List<Book>> findBooksByAuthorNameAndAuthorSurnameAndAuthorPatronymic(String name, String surname, String patronymic);

    /**
     * Find books by author name, surname, patronymic, and reader name.
     *
     * @param name       the author's name
     * @param surname    the author's surname
     * @param patronymic the author's patronymic
     * @param nameBook   the name of the reader
     * @return optional list of books found
     */
    Optional<List<Book>> findBooksByAuthorNameAndAuthorSurnameAndAuthor_PatronymicAndNameContainsIgnoreCase(String name, String surname, String patronymic, String nameBook);

    /**
     * A description of the entire Java function.
     *
     * @param name       description of parameter
     * @param surname    description of parameter
     * @param patronymic description of parameter
     * @param name1      description of parameter
     * @return description of return value
     */
    @Query("""
            select b from Book b inner join b.author.books books
            where upper(b.author.name) like upper(concat(?1, '%')) and upper(b.author.surname) like upper(concat(?2, '%')) and upper(b.author.patronymic) like upper(?3) and upper(books.name) like upper(concat('%', ?4, '%'))""")
    Optional<List<Book>> findByAuthor_NameStartsWithIgnoreCaseAndAuthor_SurnameStartsWithIgnoreCaseAndAuthor_PatronymicLikeIgnoreCaseAndAuthor_Books_NameContainsIgnoreCase(@NonNull String name, String surname, String patronymic, @NonNull String name1);
}