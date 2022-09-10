package ru.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="book_binding")
public class BookBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @NonNull
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NonNull
    @OneToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date_of_return")
    private LocalDate dateOfReturn;

    @NonNull
    @Column(name = "status")
    private Status status;

    @NonNull
    @OneToOne
    @JoinColumn(name = "library_department")
    private LibraryDepartment libraryDepartment;

}
