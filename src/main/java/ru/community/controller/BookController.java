package ru.community.controller;


import com.poiji.bind.Poiji;
import com.poiji.bind.PoijiFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.community.service.BookService;
import ru.community.entity.Book;

import java.io.File;
import java.util.List;
import java.util.Map;

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


    @PostMapping("librarian/{librarianId}/addBook")
    public Book addBooks(@RequestBody Book book,
                         @PathVariable int librarianId,
                         @RequestParam Map<String, String> param) {

        return service.addBooks(book, param, librarianId);
    }

    @PostMapping("librarian/{librarianId}/addBookFromExcel")
    public List<Book> addBooksFromExcel(@RequestParam(name = "file") MultipartFile file,
                                        @PathVariable int librarianId,
                                        @RequestParam(name = "registerOfParish") String registerOfParish ){
        return service.addBooksFromExcel(file, librarianId, registerOfParish);

    }
}
