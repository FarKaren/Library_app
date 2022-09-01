package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
}
