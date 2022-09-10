package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.BookBinding;
import ru.community.exception.LibraryException;
import ru.community.exception.Message;
import ru.community.repository.BookBindingRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookBindingService {

    private final BookBindingRepository bookBindingRepository;

    public List<BookBinding> getBookBindingByReader(int readerId) {
        return bookBindingRepository.findBookBindingByReader(readerId)
                .orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
    }

    public List<BookBinding> getBookBindingByBook(int bookId) {
        return bookBindingRepository.findBookBindingByBook(bookId)
                .orElseThrow(() -> new LibraryException(Message.BOOK_NOT_FOUND));
    }

}
