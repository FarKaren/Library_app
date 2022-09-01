package ru.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.community.entity.Book;
import ru.community.entity.BookRating;
import ru.community.service.BookRatingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRatingController {

    private final BookRatingService bookRatingService;

    @PostMapping("/rating")
    public ResponseEntity addBookRating(@Valid @RequestBody BookRating bookRating){
        bookRatingService.addBookRating(bookRating);
        return ResponseEntity.status(HttpStatus.OK).body(bookRating);
    }

    @GetMapping("/rating")
    public List<Book> getAllBooksSortedByDescendingRating(){
        return bookRatingService.getAllBooksSortedByDescendingRating();
    }

    @PutMapping("/rating/new_review/{bookId}/{readerId}")
    public ResponseEntity addNewReview(@PathVariable int bookId, @PathVariable int readerId, @RequestParam(name = "review") String text){
        bookRatingService.addNewReview(bookId, readerId, text);
        return ResponseEntity.status(HttpStatus.OK).body("Review was successfully added");
    }

    @PutMapping("/rating/new_rating/{bookId}/{readerId}")
    public ResponseEntity addRating(@PathVariable int bookId, @PathVariable int readerId, @RequestParam(name = "rating") int rating){
        bookRatingService.addRating(bookId, readerId, rating);
        return ResponseEntity.status(HttpStatus.OK).body("Rating was successfully added");
    }
}
