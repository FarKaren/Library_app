package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Book;
import ru.community.entity.BookBinding;
import ru.community.entity.Reader;
import ru.community.exception.LibraryException;
import ru.community.exception.Message;
import ru.community.repository.BookBindingRepository;
import ru.community.repository.BookRepository;
import ru.community.repository.ReaderRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookBindingService {

    private final BookBindingRepository bookBindingRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    public List<BookBinding> getBookBindingByReader(int readerId) {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
        return bookBindingRepository.findBookBindingByReader(readerId)
                .orElseThrow(() -> new LibraryException(Message.BOOK_BINDING_NOT_FOUND));
    }

    public List<BookBinding> getBookBindingByBook(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException(Message.BOOK_NOT_FOUND));
        return bookBindingRepository.findBookBindingByBook(bookId)
                .orElseThrow(() -> new LibraryException(Message.BOOK_BINDING_NOT_FOUND));
    }

}
