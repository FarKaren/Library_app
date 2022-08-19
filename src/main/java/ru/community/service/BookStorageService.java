package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.BookStorage;
import ru.community.repository.BookStorageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookStorageService {

    private BookStorageRepository bookStorageRepository;

    public List<BookStorage> getAllAvailableBooks(){
        return bookStorageRepository.findAllAvailableBooks();
    }

    public List<BookStorage> getAllBooksByDepartment(int departmentId){
        return bookStorageRepository.findAllByDepartmentId(departmentId);
    }

    public List<BookStorage> getAllAvailableBooksByDepartmentId(int departmentId){
        return bookStorageRepository.findAllAvailableBooksByDepartmentId(departmentId);
    }

    public List<BookStorage> findAllAvailableBooksByAuthor(String author){
       return bookStorageRepository.findAllAvailableBooksByAuthor(author);
    }

    public List<BookStorage> findAllAvailableBooksByGenre(String genre){
        return bookStorageRepository.findAllAvailableBooksByGenre(genre);
    }

}
