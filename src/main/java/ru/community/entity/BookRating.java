package ru.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "book_rating")
@Data
@RequiredArgsConstructor
public class BookRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @NonNull
    private String review;

    @NonNull
    @Min(1)
    @Max(5)
    private int rating;
}
