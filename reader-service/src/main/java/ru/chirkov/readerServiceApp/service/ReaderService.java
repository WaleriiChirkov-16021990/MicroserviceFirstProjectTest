package ru.chirkov.readerServiceApp.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chirkov.readerServiceApp.dao.ReaderRepository;
import ru.chirkov.readerServiceApp.model.Reader;
import ru.chirkov.readerServiceApp.util.exceptionCustom.BadRequestReaderCustomeException;
import ru.chirkov.readerServiceApp.util.exceptionCustom.NotFoundReaderCustomeException;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = {Exception.class, BadRequestReaderCustomeException.class},
        dontRollbackOn = {NotFoundReaderCustomeException.class})
public class ReaderService {

    private final ReaderRepository readerRepository;

    /**
     * Retrieves a list of readers.
     *
     * @return the list of readers
     */
    public List<Reader> getBooks() {
        return readerRepository.findAll();
    }

    /**
     * Retrieves a reader by its ID.
     *
     * @param id the ID of the reader to retrieve
     * @return the reader with the specified ID
     */
    public Reader getReaderById(Long id) {
        return readerRepository.findById(id).orElseThrow(() ->
                new NotFoundReaderCustomeException("Reader with id = " + id + " not found."));
    }


    /**
     * A method to retrieve a list of books by name.
     *
     * @param name the name of the book to search for
     * @return a list of books with the specified name
     */
    public List<Reader> getReadersBySurname(String name) {
        return readerRepository.findBySurname(name);
    }

    /**
     * Saves a reader.
     *
     * @param reader the reader to be saved
     * @return the saved reader
     */
    public Reader saveReader(Reader reader) {
        return readerRepository.save(reader);
    }

    /**
     * Delete a reader from the repository.
     *
     * @param reader the reader to be deleted
     * @return void
     */
    public void deleteReader(Reader reader) {
        readerRepository.delete(reader);
    }

    /**
     * Deletes a reader by its ID.
     *
     * @param id the ID of the reader to be deleted
     * @return void
     */
    public void deleteReaderById(Long id) {
        readerRepository.deleteById(id);
    }

    /**
     * Updates a reader with the given ID.
     *
     * @param id     the ID of the reader to update
     * @param reader the updated reader object
     * @return the updated reader object
     */
    public Reader updateBook(Long id, Reader reader) {
        reader.setId(id);
        return readerRepository.save(reader);
    }

    /**
     * A function to update the name of a reader by its ID.
     *
     * @param id   the ID of the reader
     * @param name the new name of the reader
     * @return the updated reader object
     */
    public Reader patchNameBookById(Long id, String name) {
        Reader reader = getReaderById(id);
        reader.setName(name);
        return readerRepository.save(reader);
    }


}
