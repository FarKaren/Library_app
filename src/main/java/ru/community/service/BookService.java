package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.*;
import ru.community.repository.*;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Book addBook(Book book){
       return repository.save(book);
    }

    public Book getBook(String author, String title){
        return repository.findByAuthorAndTitle(author, title);
    }

    public List<Book> getAllBooks(){
        return repository.findAll();
    }




}
