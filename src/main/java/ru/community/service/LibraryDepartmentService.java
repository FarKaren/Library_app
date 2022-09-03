package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.LibraryDepartment;
import ru.community.exception.Message;
import ru.community.exception.SmartToolsException;
import ru.community.repository.LibraryDepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryDepartmentService {

    private final LibraryDepartmentRepository libraryDepartmentRepository;

    public void addNewDepartment(LibraryDepartment libraryDepartment) {
        if (libraryDepartmentRepository.findByTitle(libraryDepartment.getTitle()).isPresent()) {
            throw new SmartToolsException(Message.DEPARTMENT_ALREADY_EXISTS);
        } else {
            libraryDepartmentRepository.save(libraryDepartment);
        }
    }

    public LibraryDepartment getDepartmentByTitle(String title) {
        return libraryDepartmentRepository.findByTitle(title).orElseThrow(() -> new SmartToolsException(Message.DEPARTMENT_NOT_FOUND));
    }

    public List<LibraryDepartment> getAllDepartment() {
        return libraryDepartmentRepository.findAll();
    }
}
