package ru.chirkov.bookServiceApp.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chirkov.bookServiceApp.dao.AuthorRepository;
import ru.chirkov.bookServiceApp.model.Author;
import ru.chirkov.bookServiceApp.util.exceptionCustom.BadRequestAuthorException;
import ru.chirkov.bookServiceApp.util.exceptionCustom.NotFoundAuthorCustomeException;

import java.util.List;

@Service
@Transactional(rollbackOn = {Exception.class, BadRequestAuthorException.class}, dontRollbackOn = {NotFoundAuthorCustomeException.class})
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Retrieves the author with the given ID.
     *
     * @param authorId the ID of the author to retrieve
     * @return the author with the given ID
     */
    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(() ->
                new NotFoundAuthorCustomeException("Author with id = " + authorId + " not found."));
    }

    /**
     * Retrieves a list of authors from the author repository.
     *
     * @return the list of authors
     */
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Retrieves a list of authors by name.
     *
     * @param name the name of the author to search for
     * @return a list of authors with the specified name
     */
    public List<Author> getAuthorsByName(String name) {
        return authorRepository.findAuthorsByName(name);
    }

    /**
     * Retrieves a list of authors with the given surname.
     *
     * @param surname the surname of the authors to retrieve
     * @return a list of authors with the given surname
     */
    public List<Author> getAuthorsBySurname(String surname) {
        return authorRepository.findAuthorsBySurname(surname);
    }

    /**
     * A description of the entire Java function.
     *
     * @param patronymic description of parameter
     * @return description of return value
     */
    public List<Author> getAuthorsByPatronymic(String patronymic) {
        return authorRepository.findAuthorsByPatronymic(patronymic);
    }

    /**
     * Retrieves a list of authors by name and surname.
     *
     * @param name    the name of the author
     * @param surname the surname of the author
     * @return a list of authors with the provided name and surname
     */
    public List<Author> getAuthorsByNameAndSurname(String name, String surname) {
        return authorRepository.findAuthorsByNameAndSurname(name, surname);
    }

    /**
     * Retrieves a list of authors by their name, surname, and patronymic.
     *
     * @param name       the name of the author
     * @param surname    the surname of the author
     * @param patronymic the patronymic of the author
     * @return a list of authors matching the provided name, surname, and patronymic
     */
    public List<Author> getAuthorsByNameAndSurnameAndPatronymic(String name, String surname, String patronymic) {
        return authorRepository.findAuthorsByNameAndSurnameAndPatronymic(name, surname, patronymic);
    }

    /**
     * Saves an author to the database.
     *
     * @param author The author object to be saved
     * @return The saved author object
     */
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Deletes an author by their ID.
     *
     * @param authorId the ID of the author to be deleted
     * @return void
     */
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    /**
     * Updates the author's ID and saves the author.
     *
     * @param authorId the ID of the author
     * @param author   the updated author object
     * @return the saved author object
     */
    public Author putAuthor(Long authorId, Author author) {
        author.setId(authorId);
        return saveAuthor(author);
    }


}
