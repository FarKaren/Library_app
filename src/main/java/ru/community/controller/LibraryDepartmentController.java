package ru.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.community.entity.LibraryDepartment;
import ru.community.service.LibraryDepartmentService;

import java.util.List;

@Controller
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
