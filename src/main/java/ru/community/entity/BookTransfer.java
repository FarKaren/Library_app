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
    @JoinColumn(name = "book_storage_id")
    private BookStorage bookStorage;

    @Column(name = "cause_of_parish")
    private CauseOfParish causeOfParish;

    @Column(name = "comment")
    private String comment;

    @OneToOne
    @JoinColumn(name = "librarian_id")
    private Librarian librarian;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "register_date")
    private LocalDate registerDate;
}
