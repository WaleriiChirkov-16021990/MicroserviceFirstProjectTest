package ru.chirkov.readerServiceApp.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chirkov.readerServiceApp.dto.ReaderDto;
import ru.chirkov.readerServiceApp.model.Reader;
import ru.chirkov.readerServiceApp.service.ReaderService;
import ru.chirkov.readerServiceApp.util.exceptionCustom.CustomValidationException;
import ru.chirkov.readerServiceApp.util.exceptionCustom.BadRequestReaderCustomeException;
import ru.chirkov.readerServiceApp.util.exceptionCustom.NotFoundReaderCustomeException;
import ru.chirkov.readerServiceApp.util.response.AddErrorMessage;
import ru.chirkov.readerServiceApp.util.response.ResponseByException;
import ru.chirkov.readerServiceApp.util.validationCustom.ReaderCustomeValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/readers")
public class ReaderController {

    private final ReaderService readerService;
    private final ModelMapper modelMapper;

    private final ReaderCustomeValidator customValidator;


    @GetMapping
    public ResponseEntity<List<ReaderDto>> getBooks() {
        return ResponseEntity.ok(readerService.getReaders()
                .stream()
                .map((element) -> modelMapper.map(element, ReaderDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReaderDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(readerService.getReaderById(id), ReaderDto.class));
    }

    @GetMapping(value = "/search/{name}")
    public ResponseEntity<List<ReaderDto>> getBooksByName(@PathVariable String name) {
        return ResponseEntity.ok(readerService.getReadersBySurname(name)
                .stream()
                .map((element) -> modelMapper.map(element, ReaderDto.class))
                .collect(Collectors.toList()));
    }


    @GetMapping(value = "/search/surname/{surname}")
    public ResponseEntity<List<ReaderDto>> getBooksByAuthorNameAndSurname(@PathVariable String surname) {
        return ResponseEntity.ok(readerService.getReadersBySurname(surname)
                .stream()
                .map((element) -> modelMapper.map(element, ReaderDto.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ReaderDto> addBook(@RequestBody @Valid ReaderDto readerDto, BindingResult result) {
        Reader reader = modelMapper.map(readerDto, Reader.class);
        customValidator.validate(reader, result);
        if (result.hasErrors()) {
            throw new CustomValidationException(
                    AddErrorMessage.addErrorMessageFromBindingResult(result));
        }
        return ResponseEntity.ok(modelMapper.map(readerService.saveReader(reader), ReaderDto.class));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@RequestBody @Valid Reader reader) {
        readerService.deleteReader(reader);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        readerService.deleteReaderById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReaderDto> updateBook(@PathVariable Long id, @RequestBody @Valid ReaderDto readerDto
            , BindingResult result) {
        Reader reader = modelMapper.map(readerDto, Reader.class);
        reader.setId(id);
        customValidator.validate(reader, result);
        if (result.hasErrors()) {
            throw new CustomValidationException(
                    AddErrorMessage.addErrorMessageFromBindingResult(result));
        }
        ;
        return ResponseEntity.ok(modelMapper.map(readerService.updateReader(id, reader), ReaderDto.class));
    }

    @PatchMapping(value = "/{id}/{name}")
    public ResponseEntity<ReaderDto> patchBook(@PathVariable Long id, @PathVariable String name
            , BindingResult result) {
        Reader readerPatching = readerService.getReaderById(id);
        readerPatching.setName(name);
        customValidator.validate(readerPatching, result);
        if (result.hasErrors()) {
            throw new CustomValidationException(
                    AddErrorMessage.addErrorMessageFromBindingResult(result));
        }
        return ResponseEntity.ok(modelMapper.map(readerService.patchNameBookById(id, name), ReaderDto.class));
    }
//
//    @PatchMapping(value = "/{id}/{authorId}/")
//    public ResponseEntity<ReaderDto> patchBook(@PathVariable Long id, @PathVariable Long authorId
//            , BindingResult result) {
//        Reader readerPatching = readerService.getBookById(id);
//        customValidator.validate(readerPatching, result);
//        if (result.hasErrors()) {
//            throw new CustomValidationException(
//                    AddErrorMessage.addErrorMessageFromBindingResult(result));
//        }
//        return ResponseEntity.ok(modelMapper.map(bookService.patchAuthorBookById(id, authorId), BookDto.class));
//    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ResponseByException> handleValidationException(CustomValidationException e) {
        return new ResponseEntity<>(new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundReaderCustomeException.class)
    public ResponseEntity<ResponseByException> handleNotFoundException(NotFoundReaderCustomeException e) {
        return new ResponseEntity<>(new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName())
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestReaderCustomeException.class)
    public ResponseEntity<ResponseByException> handleNotFoundException(BadRequestReaderCustomeException e) {
        return new ResponseEntity<>(new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName())
                , HttpStatus.NOT_FOUND);
    }
}
