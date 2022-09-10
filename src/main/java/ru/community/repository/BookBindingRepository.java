package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.community.entity.BookBinding;

import java.util.List;
import java.util.Optional;


public interface BookBindingRepository extends JpaRepository<BookBinding, Integer> {

    @Query(value = "from BookBinding where reader.id = ?1")
    Optional<List<BookBinding>> findBookBindingByReader(int readerId);

    @Query(value = "from BookBinding where book.id = ?1")
    Optional<List<BookBinding>> findBookBindingByBook(int bookId);
}
