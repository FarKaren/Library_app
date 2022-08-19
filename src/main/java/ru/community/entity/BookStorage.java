package ru.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_storage")
public class BookStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "total_count")
    private int totalCount;
    @Column(name = "available_count")
    private int availableCount;
    @Column(name = "department_id")
    private int departmentId;
}
