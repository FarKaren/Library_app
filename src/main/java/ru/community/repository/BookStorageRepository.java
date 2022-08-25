package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.community.entity.Book;
import ru.community.entity.BookStorage;

import java.util.List;

@Repository
public interface BookStorageRepository extends JpaRepository<BookStorage, Integer> {

    List<BookStorage> findAllByDepartmentId(int id);

    @Query(value = " select b from BookStorage b where b.availableCount is not null")
    List<BookStorage> findAllAvailableBooks();

    List<BookStorage> findAllAvailableBooksByDepartmentId(int id);

    BookStorage findBookStorageByBook(Book book);

}
