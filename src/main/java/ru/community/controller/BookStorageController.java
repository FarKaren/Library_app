package ru.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.community.entity.BookStorage;
import ru.community.services.BookStorageService;

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

}
