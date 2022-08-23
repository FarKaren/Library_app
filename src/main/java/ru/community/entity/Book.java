package ru.community.entity;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.community.emun.Genre;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @ExcelRow
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ExcelCellName("Author")
    @Column(name = "author")
    private String author;

    @ExcelCellName("Title")
    @Column(name = "title")
    private String title;

    @ExcelCellName("PublisherYear")
    @Column(name = "publisher_year")
    private int publisherYear;

    @ExcelCellName("Genre")
    @Column(name = "genre")
    private Genre genre;

    @ExcelCellName("Publisher")
    @Column(name = "publisher")
    private String publisher;

    @ExcelCellName("CountOfPage")
    @Column(name = "count_of_page")
    private int countOfPage;

}
