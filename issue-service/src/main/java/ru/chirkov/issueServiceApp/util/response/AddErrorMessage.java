package ru.chirkov.issueServiceApp.util.response;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AddErrorMessage {
    /**
     * Generates an error message from the given BindingResult.
     *
     * @param  result   the BindingResult containing the field errors
     * @return          the error message generated from the field errors
     */
    public static String addErrorMessageFromBindingResult(BindingResult result) {
        AtomicReference<StringBuilder> buffer = new AtomicReference<>(new StringBuilder());
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            buffer.get().append(fieldError.getField());
            buffer.get().append(" - ");
            buffer.get().append(fieldError.getDefaultMessage());
            buffer.get().append(";\n");
        }
        return buffer.get().toString();

    }
}
