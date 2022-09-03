package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Librarian;
import ru.community.exception.Message;
import ru.community.exception.SmartToolsException;
import ru.community.repository.LibraryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository repository;

    public void addLibrarian(Librarian librarian){
        if (repository.findByNameAndSurnameAndDateOfBirth(librarian.getName(), librarian.getSurname(), librarian.getDateOfBirth()).isPresent()) {
            throw new SmartToolsException(Message.LIBRARIAN_ALREADY_EXISTS);
        } else {
            repository.save(librarian);
        }
    }

    public Librarian getLibrarian(int librarianId){
        return repository.findById(librarianId).orElseThrow(() -> new SmartToolsException(Message.LIBRARIAN_NOT_FOUND));
    }

    public List<Librarian> getAllLibrarian(){
       return repository.findAll();
    }

    public void deleteLibrarian(int id){
        Librarian librarian = repository.findById(id).orElseThrow(() -> new SmartToolsException(Message.LIBRARIAN_NOT_FOUND));
        repository.delete(librarian);
    }
}
