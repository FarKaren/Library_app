package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.BookStorage;
import ru.community.entity.LibraryDepartment;
import ru.community.repository.BookStorageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookStorageService {

    private final BookStorageRepository bookStorageRepository;

    public List<BookStorage> getAllAvailableBooks(){
        return bookStorageRepository.findAllAvailableBooks();
    }

    public List<BookStorage> getAllBooksByLibraryDepartment(LibraryDepartment libraryDepartment){
        return bookStorageRepository.findAllByLibraryDepartment(libraryDepartment);
    }

    public List<BookStorage> getAllAvailableBooksByLibraryDepartment(LibraryDepartment libraryDepartment){
        return bookStorageRepository.findAllAvailableBooksByLibraryDepartment(libraryDepartment);
    }
}
