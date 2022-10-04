package ru.community.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.community.entity.BookBinding;
import ru.community.service.BookBindingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookBindingController {

    private final BookBindingService bookBindingService;

    @GetMapping("/book_status_by_reader/{readerId}")
    public ResponseEntity<List<BookBinding>> getBooksStatusByReader(@PathVariable int readerId){
        List<BookBinding> bookBindings = bookBindingService.getBookBindingByReader(readerId);
        return ResponseEntity.ok(bookBindings);
    }

    @GetMapping("/book_status_by_book/{bookId}")
    public ResponseEntity<List<BookBinding>> getBooksStatusByBook(@PathVariable int bookId){
        List<BookBinding> bookBindings = bookBindingService.getBookBindingByBook(bookId);
        return ResponseEntity.ok(bookBindings);
    }
}
