package ru.community.entity;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
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
    @ExcelRow
    private int id;

    @NonNull
    @Column(name = "author")
    @ExcelCellName("Author")
    @CsvBindByName(column = "Author")
    private String author;

    @NonNull
    @Column(name = "title")
    @ExcelCellName("Title")
    @CsvBindByName(column = "Title")
    private String title;

    @NonNull
    @Column(name = "publisher_year")
    @ExcelCellName("PublisherYear")
    @CsvBindByName(column = "PublisherYear")
    private int publisherYear;

    @NonNull
    @Column(name = "genre")
    @ExcelCellName("Genre")
    @CsvCustomBindByName(column = "Genre", converter = GenreCsvConverter.class)
    private Genre genre;

    @NonNull
    @Column(name = "publisher")
    @ExcelCellName("Publisher")
    @CsvBindByName(column = "Publisher")
    private String publisher;

    @NonNull
    @Column(name = "count_of_page")
    @ExcelCellName("CountOfPage")
    @CsvBindByName(column = "CountOfPage")
    private int countOfPage;

}
