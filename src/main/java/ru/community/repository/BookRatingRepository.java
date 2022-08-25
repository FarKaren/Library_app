package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.BookRating;

import java.util.List;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {

    List<BookRating> findByOrderByRatingDesc();

    BookRating findByBookIdAndReaderId(int bookId, int readerId);
}
