package ru.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.community.entity.Librarian;
import ru.community.entity.LibrarianDepartment;
import ru.community.entity.LibraryDepartment;

@Repository
public interface LibrarianDepartmentRepository extends JpaRepository<LibrarianDepartment, Integer> {

    @Query(value = "from LibrarianDepartment where librarian = :librarian")
    LibrarianDepartment findByLibrarian(@Param("librarian") Librarian librarians);

}
