package ru.community.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "book_transfer")
public class BookTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "count")
    private int count;

    @OneToOne
    @JoinColumn(name = "from_department_id")
    private LibraryDepartment from;

    @OneToOne
    @JoinColumn(name = "to_department_id")
    private LibraryDepartment to;

    @Column(name = "comment")
    private String comment;

    @OneToOne
    @JoinColumn(name = "librarian_id")
    private Librarian librarian;

    @Column(name = "reason_of_parish")
    private Reason reason;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "register_date")
    private LocalDate registerDate;
}
