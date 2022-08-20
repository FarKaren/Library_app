package ru.community.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.community.entity.Book;
import ru.community.entity.BookStorage;
import ru.community.entity.Librarian;
import ru.community.entity.LibraryDepartment;
import ru.community.exception.LibrarianNotFound;
import ru.community.service.LibraryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibrarianController {

    private final LibraryService service;


    @PostMapping("/librarian")
    public ResponseEntity<Librarian> addLibrarian(@Valid @RequestBody Librarian librarian) throws LibrarianNotFound {
        service.addLibrarian(librarian);
        return ResponseEntity.ok(librarian);
    }

    @GetMapping("/librarian/{id}")
    public Librarian getLibrarian(@PathVariable int id){
        return service.getLibrarian(id);
    }

    @GetMapping("/librarian/list")
    public List<Librarian> getAllLibrarian(){
        return service.getAllLibrarian();
    }

    @DeleteMapping("/librarian/delete/{id}")
    public void deleteLibrarian(@PathVariable int id){
        service.deleteLibrarian(id);
    }

    @PostMapping("/books/all")
    public List<BookStorage> addBooks(@RequestParam(name = "librarianId") int id){
        return service.addBooks(id);
    }
}
