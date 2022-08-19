package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Librarian;
import ru.community.exception.LibrarianNotFound;
import ru.community.repo.LibraryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository repository;

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


}
