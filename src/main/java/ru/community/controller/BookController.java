package ru.community.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.community.service.BookService;
import ru.community.entity.Book;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping("/book")
    public Book addBook(@RequestBody Book book){
        return service.addBook(book);
    }

    @GetMapping("/book")
    public Book getBook(@RequestParam(name = "author") String author, @RequestParam(name = "title") String title){
        return service.getBook(author, title);
    }

    @GetMapping("/book/list")
    public List<Book> getAllBook(){
        return service.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book = service.getBookById(id);
        return ResponseEntity.ok(book);
    }
}
