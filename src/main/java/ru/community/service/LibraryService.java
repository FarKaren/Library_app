package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.*;
import ru.community.exception.LibrarianNotFound;
import ru.community.repo.LibraryRepository;
import ru.community.repository.BookRepository;
import ru.community.repository.BookStorageRepository;
import ru.community.repository.LibrarianDepartmentRepository;
import ru.community.repository.LibraryDepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository repository;
    private LibrarianDepartmentRepository librarianDepartmentRepository;
    private BookRepository bookRepository;
    private LibraryDepartmentRepository libraryDepartmentRepository;
    private BookStorageRepository bookStorageRepository;


    public void addLibrarian(Librarian librarian){
        repository.save(librarian);
    }

    public Librarian getLibrarian(int librarianId){
        return repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
    }

    public List<Librarian> getAllLibrarian(){
       return repository.findAll();
    }

    public void deleteLibrarian(int id){
        Librarian librarian = repository.findById(id).orElseThrow(LibrarianNotFound::new);
        repository.delete(librarian);
    }

    public List<BookStorage> addBooks(int librarianId){
        List<Book> books = bookRepository.findAll();
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarianId(librarianId);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        for (Book book : books) {
            BookStorage bookStorage = new BookStorage();
            //bookStorage.set
        }
        return null;
    }




}
