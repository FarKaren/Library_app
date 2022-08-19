package ru.community.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_storage")
public class BookStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "total_count")
    private int totalCount;
    @Column(name = "available_count")
    private int availableCount;
    @Column(name = "department_id")
    private int departmentId;
}
