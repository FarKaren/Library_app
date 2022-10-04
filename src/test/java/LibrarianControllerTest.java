import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.Application;
import ru.community.entity.Book;
import ru.community.entity.BookStorage;
import ru.community.entity.Genre;
import ru.community.entity.Librarian;
import ru.community.entity.LibrarianDepartment;
import ru.community.entity.LibraryDepartment;
import ru.community.entity.ReasonOfParish;
import ru.community.repository.BookStorageRepository;
import ru.community.repository.LibrarianDepartmentRepository;
import ru.community.repository.LibrarianRepository;
import ru.community.repository.LibraryDepartmentRepository;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Log4j2
public class LibrarianControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookStorageRepository bookStorageRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private LibraryDepartmentRepository libraryDepartmentRepository;

    @Autowired
    private LibrarianDepartmentRepository librarianDepartmentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Add book to BookRepository and BookStorage")
    public void addBookTest() throws Exception {
        Book book = new Book(1, "Джон Толкиен", "Хоббит", 1973,
                Genre.NOVEL, "George Allen & Unwin", 208);

        Librarian librarian = createTestLibrarian(1, "Ксения", "Сапогова"
                , "89189504203", LocalDate.of(1994, 3, 27));

        LibraryDepartment libraryDepartment = createTestLibraryDepartment(1, "Московская Библиотека",
                "ул.Ленина,10");

        LibrarianDepartment librarianDepartment = createTestLibrarianDepartment(1, librarian, libraryDepartment
                , LocalDate.of(2015, 3, 21), LocalDate.of(2022, 4, 14));


        this.mockMvc.perform(
                        post("/librarian/{librarianId}/addBooks", 1)
                                .content(objectMapper.writeValueAsString(book))
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("bookCount", "13")
                                .param("reasonOfParish", String.valueOf(ReasonOfParish.WRITE_OFF))
                                .param("comment", "some comment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("author").value("Джон Толкиен"))
                .andExpect(jsonPath("title").value("Хоббит"))
                .andExpect(jsonPath("publisherYear").value(1973))
                .andExpect(jsonPath("genre").value("роман"))
                .andExpect(jsonPath("publisher").value("George Allen & Unwin"))
                .andExpect(jsonPath("countOfPage").value(208));


        BookStorage bookStorage = bookStorageRepository.findBookStorageByBook(book).orElse(null);
        Assertions.assertEquals(bookStorage.getBook(), book);
    }

    @Test
    @DisplayName("Edit librarian")
    public void testEditLibrarian() throws Exception {
        Librarian librarian = createTestLibrarian(1, "Ксения", "Сапогова"
                , "89189504203", LocalDate.of(1994, 3, 27));
        Librarian editLibrarian = new Librarian(1, "Ксения", "Сапогова",
                "89504602030", LocalDate.of(1994, 3, 27));

        mockMvc.perform(
                    put("/librarian/{id}/me/edit", editLibrarian.getId())
                    .content(objectMapper.writeValueAsString(editLibrarian))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Ксения"))
                .andExpect(jsonPath("surname").value("Сапогова"))
                .andExpect(jsonPath("phoneNumber").value("89504602030"))
                .andExpect(jsonPath("dateOfBirth").value("27.03.1994"));
    }


    public Librarian createTestLibrarian(int id, String name, String surname, String phoneNumber, LocalDate dateOfBirth) {
        Librarian librarian = new Librarian(id, name, surname, phoneNumber, dateOfBirth);
        return librarianRepository.save(librarian);

    }

    public LibraryDepartment createTestLibraryDepartment(int id, String title, String address) {
        LibraryDepartment libraryDepartment = new LibraryDepartment(id, title, address);
        return libraryDepartmentRepository.save(libraryDepartment);
    }

    public LibrarianDepartment createTestLibrarianDepartment(int id, Librarian librarian,
                                                             LibraryDepartment libraryDepartment, LocalDate hireDate, LocalDate dismissDate) {
        LibrarianDepartment librarianDepartment = new LibrarianDepartment(id, librarian, libraryDepartment
                , hireDate, dismissDate);
        return librarianDepartmentRepository.save(librarianDepartment);
    }


}
