package ru.community.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Book findByAuthorAndTitle(String author, String title);

}

