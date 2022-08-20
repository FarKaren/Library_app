package ru.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.community.entity.Book;
import ru.community.entity.BookStorage;
import ru.community.entity.LibraryDepartment;
import ru.community.repository.LibrarianDepartmentRepository;
import ru.community.service.BookStorageService;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookStorageController {

    private BookStorageService bookStorageService;


    @GetMapping("/storage")
    public List<BookStorage> getAllAvailableBooks(){
        return bookStorageService.getAllAvailableBooks();
    }

    @GetMapping("/storage/all/{departmentId}")
    public List<BookStorage> getAllBooksByDepartment(@PathVariable int departmentId){
        return bookStorageService.getAllBooksByDepartment(departmentId);
    }

    @GetMapping("/storage/available/{departmentId}")
    public List<BookStorage> getAllAvailableBooksByDepartmentId(@PathVariable int departmentId){
        return bookStorageService.getAllAvailableBooksByDepartmentId(departmentId);
    }

    @GetMapping("books/all")
    public ResponseEntity<List<BookStorage>> getAllBooks(@RequestParam(value = "departmentId") int departmentId,
                                                        @RequestParam(name = "author") String author,
                                                        @RequestParam(value = "genre") String genre){
        if(departmentId != 0)
           return ResponseEntity.ok(bookStorageService.getAllAvailableBooksByDepartmentId(departmentId));
        if(author != null)
           return ResponseEntity.ok(bookStorageService.findAllAvailableBooksByAuthor(author));
        if(genre != null)
           return ResponseEntity.ok(bookStorageService.findAllAvailableBooksByGenre(genre));
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
