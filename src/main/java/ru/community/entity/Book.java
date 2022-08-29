package ru.community.entity;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.community.converter.csv.GenreCsvConverter;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "author")
    @CsvBindByName(column = "Author")
    private String author;

    @Column(name = "title")
    @CsvBindByName(column = "Title")
    private String title;

    @Column(name = "publisher_year")
    @CsvBindByName(column = "PublisherYear")
    private int publisherYear;

    @Column(name = "genre")
    @CsvCustomBindByName(column = "Genre", converter = GenreCsvConverter.class)
    private Genre genre;

    @Column(name = "publisher")
    @CsvBindByName(column = "Publisher")
    private String publisher;

    @Column(name = "count_of_page")
    @CsvBindByName(column = "CountOfPage")
    private int countOfPage;

}
