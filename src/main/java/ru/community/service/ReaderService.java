package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.BookBinding;
import ru.community.dto.RateRequestDto;
import ru.community.entity.Book;
import ru.community.entity.BookRating;
import ru.community.entity.Reader;
import ru.community.entity.Status;
import ru.community.entity.Genre;
import ru.community.exception.LibraryException;
import ru.community.exception.Message;
import ru.community.mapper.BookRatingMapper;
import ru.community.repository.BookRatingRepository;
import ru.community.repository.BookRepository;
import ru.community.repository.BookBindingRepository;
import ru.community.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;
    private final BookBindingRepository bookBindingRepository;
    private final BookRepository bookRepository;
    private final BookRatingMapper mapper;
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

    public BookRating addFeedbackAndRate(int readerId, int bookId, RateRequestDto requestDto){
        Reader reader = repository.findById(readerId).orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new LibraryException(Message.BOOK_NOT_FOUND));
        BookRating bookRating = mapper.ReteRequestDtoToBookRating(requestDto);
        bookRating.setBook(book);
        bookRating.setReader(reader);
        return bookRatingRepository.save(bookRating);

    }

    public List<BookBinding> getBookBindingByReaderAndStatus(int readerId, List<Status> statuses) {
        repository.findById(readerId).orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
        return bookBindingRepository.findBookBindingByReader(readerId)
                .orElseThrow(() -> new LibraryException(Message.BOOK_BINDING_NOT_FOUND)).stream()
                .filter(bookBinding -> statuses.contains(bookBinding.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Book> getRecommendedBooksByGenre(int readerId) {
        repository.findById(readerId).orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));
        var bookReaderList = bookBindingRepository.findBookBindingByReader(readerId)
                .orElseThrow(() -> new LibraryException(Message.BOOK_BINDING_NOT_FOUND)).stream()
                .map(bookBinding -> bookBinding.getBook())
                .collect(Collectors.toList());
        List<Genre> sortedPopularGenres = new ArrayList<>();
        bookReaderList.stream()
                .collect(Collectors.groupingBy(book -> book.getGenre(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Genre, Long>comparingByValue().reversed())
                .forEachOrdered(genreLongEntry -> sortedPopularGenres.add(genreLongEntry.getKey()));
        var books = bookRepository.findAll().stream()
                .filter(book -> !bookReaderList.contains(book))
                .collect(Collectors.toList());

        List<Book> recommendedBooks = new ArrayList<>();
        for (Genre genre : sortedPopularGenres) {
            recommendedBooks.addAll(books.stream()
                    .filter(book -> book.getGenre().equals(genre))
                    .collect(Collectors.toList()));
        }
        return recommendedBooks;
    }
}
