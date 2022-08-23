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

    @Column(name = "register_of_parish")
    private String registerOfParish;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "register_date")
    private LocalDate registerDate;
}
