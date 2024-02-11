package ru.chirkov.readerServiceApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.chirkov.readerServiceApp.model.Reader;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    /**
     * Finds a reader by its name.
     *
     * @param name the name of the reader to find
     * @return an Optional containing the reader if found, otherwise empty
     */
    Optional<Reader> findByName(String name);


    /**
     * A method to find readers by Surname.
     *
     * @param name the name of the reader to search for
     * @return a list of books with the given name
     */

    List<Reader> findBySurname(String name);

}