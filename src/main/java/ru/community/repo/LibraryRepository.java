package ru.community.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.Librarian;

@Repository
public interface LibraryRepository extends JpaRepository<Librarian, Integer> {
}
