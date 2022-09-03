package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.Librarian;
import ru.community.entity.Reader;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {

    Optional<Reader> findByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate date);

}
