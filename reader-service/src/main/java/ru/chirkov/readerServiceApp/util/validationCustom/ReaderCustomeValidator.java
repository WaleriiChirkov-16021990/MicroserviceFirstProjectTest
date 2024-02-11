package ru.chirkov.readerServiceApp.util.validationCustom;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chirkov.readerServiceApp.model.Reader;
import ru.chirkov.readerServiceApp.service.ReaderService;

@Component
@AllArgsConstructor
public class ReaderCustomeValidator implements Validator {

    private final ReaderService readerService;


    /**
     * Check if the class is supported.
     *
     * @param clazz The class to check support for
     * @return true if the class is supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Reader.class);
    }

    /**
     * Validates the given object and errors.
     *
     * @param target the object to validate
     * @param errors the errors to record validation failures
     */
    @Override
    public void validate(Object target, Errors errors) {
        Reader reader = (Reader) target;
        if (readerService.getReaderById(reader.getId()) != null) {
            errors.rejectValue("id", "8989", "Reader with this id already exists");

        }
    }
}
