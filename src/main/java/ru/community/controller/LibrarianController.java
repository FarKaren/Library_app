package ru.community.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.community.entity.Book;
import ru.community.entity.BookBinding;
import ru.community.entity.Librarian;
import ru.community.entity.ReasonOfParish;
import ru.community.service.BookBindingService;
import ru.community.service.LibraryService;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class LibrarianController {

    private final LibraryService service;
    private final BookBindingService bookBindingService;


    @PostMapping("/librarian")
    public ResponseEntity<Librarian> addLibrarian(@Valid @RequestBody Librarian librarian) {
        service.addLibrarian(librarian);
        return ResponseEntity.ok(librarian);
    }

    @GetMapping("/librarian/{id}")
    public Librarian getLibrarian(@PathVariable int id) {
        return service.getLibrarian(id);
    }

    @GetMapping("/librarian/list")
    public List<Librarian> getAllLibrarian() {
        return service.getAllLibrarian();
    }

    @DeleteMapping("/librarian/delete/{id}")
    public void deleteLibrarian(@PathVariable int id) {
        service.deleteLibrarian(id);
    }

    @PostMapping("librarian/{librarianId}/addBooks")
    public Book addBooks(@RequestBody Book book,
                         @PathVariable int librarianId,
                         @RequestParam(name = "bookCount") int bookCount,
                         @RequestParam(name = "reasonOfParish") ReasonOfParish reasonOfParish,
                         @RequestParam(name = "comment") String comment)  {

        return service.addBooks(book, bookCount, reasonOfParish, librarianId, comment);
    }

    @PostMapping("librarian/{librarianId}/addBooksFromFile")
    public List<Book> addBooksFromFile(@RequestParam(name = "file") MultipartFile file,
                                       @PathVariable int librarianId,
                                       @RequestParam(name = "reasonOfParish") ReasonOfParish reasonOfParish,
                                       @RequestParam(name = "comment") String comment) {
        return service.addBooksFromFile(file, librarianId, reasonOfParish, comment);
    }

    @PostMapping("/librarian/addBookAndReaderToBindingBooks/{departmentId}/{readerId}")
    public ResponseEntity<List<BookBinding>> addBookAndReaderToBindingBooks(@PathVariable int departmentId,
                                                                            @PathVariable int readerId,
                                                                            @RequestParam List<Integer> booksId){
        List<BookBinding> bookBindings = service.addBookAndReaderToBindingBooks(readerId, departmentId, booksId);
        return ResponseEntity.ok(bookBindings);
    }
}
