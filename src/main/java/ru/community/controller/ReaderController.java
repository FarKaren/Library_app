package ru.community.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.community.entity.Book;
import ru.community.entity.BookBinding;
import ru.community.entity.Reader;
import ru.community.entity.Status;
import ru.community.service.ReaderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService service;

    @PostMapping("/reader")
    public ResponseEntity<Reader> addReader(@Valid @RequestBody Reader reader) {
        service.addReader(reader);
        return ResponseEntity.ok(reader);
    }

    @GetMapping("/reader/{id}/me")
    public Reader getReader(@PathVariable int id) {
        return service.getReader(id);
    }

    @PutMapping("/reader/{id}/me/edit")
    public ResponseEntity<Reader> editReader(@PathVariable int id, @Valid @RequestBody Reader reader) {
        service.editReader(id, reader);
        return ResponseEntity.ok(service.getReader(id));
    }

    @GetMapping("/reader/list")
    public List<Reader> getAlReaders() {
        return service.getAllReaders();
    }

    @DeleteMapping("/reader/{id}")
    public void deleteReader(@PathVariable int id) {
        service.deleteReader(id);
    }

    @GetMapping("/reader/{id}/myBooks/")
    public List<BookBinding> getMyBooksByStatus(@PathVariable int id, @RequestParam(value = "status") List<Status> statuses) {
        return service.getBookBindingByReaderAndStatus(id, statuses);
    }

    @GetMapping("/reader/{id}/recommend/")
    public List<Book> getRecommendedBooksByGenre(@PathVariable int id) {
        return service.getRecommendedBooksByGenre(id);
    }
}
