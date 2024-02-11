package ru.chirkov.bookServiceApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chirkov.bookServiceApp.model.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    /**
     * Find authors by name.
     *
     * @param name the name to search for
     * @return the list of authors with the given name
     */
    List<Author> findAuthorsByName(String name);

    /**
     * Find authors by surname.
     *
     * @param surname the surname to search for
     * @return a list of authors with the specified surname
     */
    List<Author> findAuthorsBySurname(String surname);

    /**
     * Find authors by patronymic.
     *
     * @param patronymic the patronymic to search for
     * @return a list of authors with the given patronymic
     */
    List<Author> findAuthorsByPatronymic(String patronymic);

    /**
     * Finds authors by name and surname.
     *
     * @param name    the name of the author
     * @param surname the surname of the author
     * @return a list of authors matching the given name and surname
     */
    List<Author> findAuthorsByNameAndSurname(String name, String surname);

    /**
     * Finds authors by name, surname, and patronymic.
     *
     * @param name       the name of the author
     * @param surname    the surname of the author
     * @param patronymic the patronymic of the author
     * @return a list of authors that match the given name, surname, and patronymic
     */
    List<Author> findAuthorsByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);

}