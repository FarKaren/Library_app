package ru.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Book;
import ru.community.entity.BookRating;
import ru.community.repository.BookRatingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookRatingService {

    private final BookRatingRepository bookRatingRepository;


    public List<Book> getAllBooksSortedByDescendingRating(){
        return bookRatingRepository.findByOrderByRatingDesc().stream()
                .map(BookRating::getBook)
                .collect(Collectors.toList());
    }

    public void addNewReview(int bookId, int readerId, String text){
        bookRatingRepository.findByBookIdAndReaderId(bookId, readerId).setReview(text);
        bookRatingRepository.save(bookRatingRepository.findByBookIdAndReaderId(bookId, readerId));
    }

    public void addRating(int bookId, int readerId, int rating){
        bookRatingRepository.findByBookIdAndReaderId(bookId, readerId).setRating(rating);
        bookRatingRepository.save(bookRatingRepository.findByBookIdAndReaderId(bookId, readerId));
    }

    public void addBookRating(BookRating bookRating){
        bookRatingRepository.save(bookRating);
    }

}
