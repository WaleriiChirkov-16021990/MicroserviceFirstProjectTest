package ru.chirkov.readerServiceApp.api.controller;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chirkov.readerServiceApp.model.Reader;
import ru.chirkov.readerServiceApp.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reader-provider")
@AllArgsConstructor
public class ReaderProviderController {

    private final ReaderService readerService;
    private final Faker faker;

    @GetMapping("/random")
    public ResponseEntity<Reader> getReadersRandom() {
        List<Reader> readers = readerService.getReaders();
        return ResponseEntity.ok(readers.get(faker.number().numberBetween(0, readers.size() - 1)));
    }
}
