package ru.community.entity;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.*;
import ru.community.converter.csv.GenreCsvConverter;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "author")
    @CsvBindByName(column = "Author")
    private String author;

    @NonNull
    @Column(name = "title")
    @CsvBindByName(column = "Title")
    private String title;


    @Column(name = "publisher_year")
    @CsvBindByName(column = "PublisherYear")
    private int publisherYear;

    @NonNull
    @Column(name = "genre")
    @CsvCustomBindByName(column = "Genre", converter = GenreCsvConverter.class)
    private Genre genre;

    @NonNull
    @Column(name = "publisher")
    @CsvBindByName(column = "Publisher")
    private String publisher;

    @Column(name = "count_of_page")
    @CsvBindByName(column = "CountOfPage")
    private int countOfPage;

}
