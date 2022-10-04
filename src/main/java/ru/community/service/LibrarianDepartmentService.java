package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.LibrarianDepartment;
import ru.community.exception.LibraryException;
import ru.community.exception.Message;
import ru.community.repository.LibrarianDepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibrarianDepartmentService {

    private final LibrarianDepartmentRepository librarianDepartmentRepository;

    public LibrarianDepartment getLibrarianDepartment(int id){
        return librarianDepartmentRepository.findById(id)
                .orElseThrow(()-> new LibraryException(Message.LIBRARIAN_DEPARTMENT_NOT_FOUND));
    }

    public List<LibrarianDepartment> getAllLibrarianDepartment(){
        return librarianDepartmentRepository.findAll();
    }
}
