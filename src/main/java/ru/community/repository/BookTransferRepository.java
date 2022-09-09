package ru.community.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.BookTransfer;

@Repository
public interface BookTransferRepository extends JpaRepository<BookTransfer, Integer> {
}
