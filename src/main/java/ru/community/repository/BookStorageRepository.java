package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.community.entity.BookStorage;

import java.util.List;

@Repository
public interface BookStorageRepository extends JpaRepository<BookStorage, Integer> {

    List<BookStorage> findAllByDepartmentId(int id);

    @Query(value = " select b from BookStorage b where b.availableCount is not null")
    List<BookStorage> findAllAvailableBooks();

    List<BookStorage> findAllAvailableBooksByDepartmentId(int id);

    @Query(value = "from Book where author = ?1")
    List<BookStorage> findAllAvailableBooksByAuthor(String author);

    @Query(value = "from Book where genre = ?1")
    List<BookStorage> findAllAvailableBooksByGenre(String genre);


}
