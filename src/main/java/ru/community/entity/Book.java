package ru.community.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.ConstructorParameters;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "publisher_year")
    private int publisherYear;

    @Column(name = "genre")
    private String genre;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "count_of_page")
    private int countOfPage;


}
