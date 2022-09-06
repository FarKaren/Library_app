package ru.community.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.Book;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByAuthorAndTitle(String author, String title);

}

