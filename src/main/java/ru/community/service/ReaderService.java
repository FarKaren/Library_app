package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Book;
import ru.community.entity.BookRating;
import ru.community.entity.BookStorage;
import ru.community.entity.Reader;
import ru.community.exception.LibraryException;
import ru.community.exception.Message;
import ru.community.repository.BookRatingRepository;
import ru.community.repository.BookRepository;
import ru.community.repository.BookStorageRepository;
import ru.community.repository.ReaderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;
    private final BookRepository bookRepository;
    private final BookStorageRepository bookStorageRepository;
    private final BookRatingRepository bookRatingRepository;

    public void addReader(Reader reader) {
        if (repository.findByNameAndSurnameAndDateOfBirth(reader.getName(), reader.getSurname(), reader.getDateOfBirth()).isPresent()) {
            throw new LibraryException(Message.READER_ALREADY_EXISTS);
        } else {
            repository.save(reader);
        }
    }

    public Reader getReader(int id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
    }

    public List<Reader> getAllReaders() {
        return repository.findAll();
    }

    public void deleteReader(int id) {
        Reader reader = repository.findById(id).orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
        repository.delete(reader);
    }

    public Reader editReader(int id, Reader editReader) {
        var reader = repository.findById(id).orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
        reader.setPhoneNumber(editReader.getPhoneNumber());
        reader.setEmail(editReader.getEmail());
        reader.setDateOfBirth(editReader.getDateOfBirth());
        repository.save(reader);
        return reader;
    }

    public BookRating addFeedbackAndRate(int readerId, int bookId, String review, int rate){
        Reader reader = repository.findById(readerId).orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new LibraryException(Message.BOOK_NOT_FOUND));
        BookStorage bookStorage = bookStorageRepository.findBookStorageByBook(book)
                .orElseThrow(() -> new LibraryException(Message.BOOK_STORAGE_NOT_FOUND));

        BookRating bookRating = new BookRating(book, reader, review, rate);
        return bookRatingRepository.save(bookRating);

    }
}
