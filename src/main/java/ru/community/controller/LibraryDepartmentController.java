package ru.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.community.entity.LibraryDepartment;
import ru.community.service.LibraryDepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibraryDepartmentController {

    private final LibraryDepartmentService libraryDepartmentService;

    @PostMapping("/department")
    public ResponseEntity addNewDepartment(@RequestBody LibraryDepartment libraryDepartment) {
        libraryDepartmentService.addNewDepartment(libraryDepartment);
        return ResponseEntity.status(HttpStatus.OK).body("LibraryDepartment " + libraryDepartment.getTitle() + " was successfully added");
    }

    @GetMapping("/department/{title}")
    public LibraryDepartment getDepartmentByTitle(@PathVariable String title) {
        return libraryDepartmentService.getDepartmentByTitle(title);
    }

    @GetMapping("/department")
    public List<LibraryDepartment> getAllDepartment() {
        return libraryDepartmentService.getAllDepartment();
    }

}
