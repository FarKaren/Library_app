package ru.community.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByAuthorAndTitle(String author, String title);

}

