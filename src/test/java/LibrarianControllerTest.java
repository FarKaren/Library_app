import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.Application;
import ru.community.entity.*;
import ru.community.exception.BookNotFound;
import ru.community.repository.BookRepository;
import ru.community.repository.BookStorageRepository;
import ru.community.repository.LibraryDepartmentRepository;
import ru.community.repository.LibraryRepository;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class LibrarianControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private  BookRepository bookRepository;

    @Autowired
    private  BookStorageRepository bookStorageRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private  LibraryDepartmentRepository libraryDepartmentRepository;

    @Autowired
    private  MockMvc mockMvc;



    @Test
    @DisplayName("Add book to BookRepository and BookStorage")
    public void addBookTest() throws Exception {
        Book book = bookRepository.save(new Book());
//        Book book = createTestBook(-1, "Джон Толкиен", "Хоббит", 1973,
//                                                      Genre.NOVEL, "George Allen & Unwin", 208);
//
//        Librarian librarian = createTestLibrarian(-1, "Ксения", "Сапогова"
//                                    ,"89189504203265", LocalDate.of(1994, 3, 27));
//
//        LibraryDepartment libraryDepartment = createTestLibraryDepartment(-1, "Московская Библиотека",
//                                                                                            "ул.Ленина,10");

        this.mockMvc.perform(
                     post("/librarian/{librarianId}/addBook", -1)
                                     .content(objectMapper.writeValueAsString(book))
                                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(-1))
                .andExpect(jsonPath("author").value("Джон Толкиен"))
                .andExpect(jsonPath("title").value("Хоббит"))
                .andExpect(jsonPath("publisherYear").value(1973))
                .andExpect(jsonPath("genre").value(Genre.NOVEL))
                .andExpect(jsonPath("publisher").value("George Allen & Unwin"))
                .andExpect(jsonPath("countOfPage").value(208));


        Throwable exception = assertThrows(BookNotFound.class, () -> bookRepository.findById(-9));
        assertEquals("Book not found", exception.getMessage());

        BookStorage bookStorage = bookStorageRepository.findBookStorageByBook(book);
        assertEquals(bookStorage.getBook(), book);
    }

    public Book createTestBook(int id, String author, String title, int publisherYear,
                                Genre genre, String publisher, int countOfPage) {
        Book book = new Book(id, author, title, publisherYear, genre, publisher, countOfPage);
        return bookRepository.save(book);
    }

    public Librarian createTestLibrarian(int id, String name, String surname
                                         ,String phoneNumber, LocalDate dateOfBirth){
        Librarian librarian = new Librarian(id, name, surname, phoneNumber, dateOfBirth);
         return libraryRepository.save(librarian);
    }

    public LibraryDepartment createTestLibraryDepartment(int id, String title, String address){
        LibraryDepartment libraryDepartment = new LibraryDepartment(id, title, address);
        return libraryDepartmentRepository.save(libraryDepartment);
    }


}
