package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.community.entity.Book;
import ru.community.entity.BookStorage;
import ru.community.entity.LibraryDepartment;

import java.util.List;

@Repository
public interface BookStorageRepository extends JpaRepository<BookStorage, Integer> {

    List<BookStorage> findAllByLibraryDepartment(LibraryDepartment libraryDepartment);

    @Query(value = " select b from BookStorage b where b.availableCount is not null")
    List<BookStorage> findAllAvailableBooks();

    List<BookStorage> findAllAvailableBooksByLibraryDepartment(LibraryDepartment libraryDepartment);

    BookStorage findBookStorageByBook(Book book);

}
