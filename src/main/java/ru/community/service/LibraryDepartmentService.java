package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.LibraryDepartment;
import ru.community.repository.LibraryDepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryDepartmentService {

    private final LibraryDepartmentRepository libraryDepartmentRepository;

    public void addNewDepartment(LibraryDepartment libraryDepartment) {
        libraryDepartmentRepository.save(libraryDepartment);
    }

    public LibraryDepartment getDepartmentByTitle(String title) {
        return libraryDepartmentRepository.findByTitle(title);
    }

    public List<LibraryDepartment> getAllDepartment() {
        return libraryDepartmentRepository.findAll();
    }
}
