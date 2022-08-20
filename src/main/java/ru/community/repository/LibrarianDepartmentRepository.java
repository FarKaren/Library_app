package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.LibrarianDepartment;
import ru.community.entity.LibraryDepartment;

@Repository
public interface LibrarianDepartmentRepository extends JpaRepository<LibraryDepartment, Integer> {
    LibrarianDepartment findByLibrarianId(int id);
}
