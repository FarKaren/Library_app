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
    private int totalCount;
    private int availableCount;
    private int departmentId;

}
