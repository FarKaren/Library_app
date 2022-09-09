package ru.community.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.community.entity.LibrarianDepartment;
import ru.community.service.LibrarianDepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibrarianDepartmentController {

    private final LibrarianDepartmentService librarianDepartmentService;

    @GetMapping("/librarian_department/{id}")
    public ResponseEntity<LibrarianDepartment> getLibrarianDepartment(@PathVariable int id){
        LibrarianDepartment LD = librarianDepartmentService.getLibrarianDepartment(id);
        return ResponseEntity.ok(LD);
    }

    @GetMapping("/librarian_department/list")
    public ResponseEntity<List<LibrarianDepartment>> getAllLibrarianDepartment(){
        List<LibrarianDepartment> list = librarianDepartmentService.getAllLibrarianDepartment();
        return ResponseEntity.ok(list);
    }
}
