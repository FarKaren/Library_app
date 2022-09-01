package ru.community.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "librarian_department")
public class LibrarianDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "librarian_id")
    private Librarian librarian;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "department_id")
    private LibraryDepartment libraryDepartment;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "dismiss_date")
    private LocalDate dismissDate;
}
