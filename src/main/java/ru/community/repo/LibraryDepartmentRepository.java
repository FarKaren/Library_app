package ru.community.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.community.entity.LibraryDepartment;

@Repository
public interface LibraryDepartmentRepository extends JpaRepository<LibraryDepartment, Integer> {

    LibraryDepartment findByTitle(String title);
}
