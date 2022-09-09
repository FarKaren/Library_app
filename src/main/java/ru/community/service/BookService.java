package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Book;
import ru.community.exception.Message;
import ru.community.exception.LibraryException;
import ru.community.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Book addBook(Book book){
       return repository.save(book);
    }

    public Book getBook(String author, String title){

        return repository.findByAuthorAndTitle(author, title).orElseThrow(() -> new LibraryException(Message.BOOK_NOT_FOUND));
    }

    public List<Book> getAllBooks(){
        return repository.findAll();
    }

    public Book getBookById(int id){
        return repository.findById(id).orElseThrow(()-> new LibraryException(Message.BOOK_NOT_FOUND));
    }
}
