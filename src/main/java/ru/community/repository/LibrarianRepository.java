package ru.community.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.Librarian;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {

    Optional<Librarian> findByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate date);
}
