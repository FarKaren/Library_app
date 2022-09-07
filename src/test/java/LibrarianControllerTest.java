import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ContentType;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import ru.community.Application;
import ru.community.entity.*;
import ru.community.repository.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Log4j2
public class LibrarianControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStorageRepository bookStorageRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private LibraryDepartmentRepository libraryDepartmentRepository;

    @Autowired
    private LibrarianDepartmentRepository librarianDepartmentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


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
                                .param("reasonOfParish", "СПИСАНИЕ")
                                .param("comment", "some comment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("author").value("Джон Толкиен"))
                .andExpect(jsonPath("title").value("Хоббит"))
                .andExpect(jsonPath("publisherYear").value(1973))
                .andExpect(jsonPath("genre").value("роман"))
                .andExpect(jsonPath("publisher").value("George Allen & Unwin"))
                .andExpect(jsonPath("countOfPage").value(208));


        BookStorage bookStorage = bookStorageRepository.findBookStorageByBook(book);
        assertEquals(bookStorage.getBook(), book);
    }

    @Test
    @DisplayName("Add book to BookRepository and BookStorage from file")
    public void addBookFromCSVTest() throws Exception {
        Librarian librarian = createTestLibrarian(1, "Ксения", "Сапогова"
                , "89189504203", LocalDate.of(1994, 3, 27));

        LibraryDepartment libraryDepartment = createTestLibraryDepartment(1, "Московская Библиотека",
                "ул.Ленина,10");

        LibrarianDepartment librarianDepartment = createTestLibrarianDepartment(1, librarian, libraryDepartment
                , LocalDate.of(2015, 3, 21), LocalDate.of(2022, 4, 14));


        MockMultipartFile file = createFileToMultipartFileTest();
        if (file.isEmpty())
            log.info("Object is empty");
        else
            log.info("Object isn't empty");

        this.mockMvc.perform(
                        multipart("/librarian/{librarianId}/addBooksFromFile", 1)
                                .file(file)
                                .param("bookCount", "13")
                                .param("reasonOfParish", "СПИСАНИЕ")
                                .param("comment", "some comment")
                                .param("fileFormat", "csv"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].author").value("Стивен Кинг"))
                .andExpect(jsonPath("$[0].title").value("Тёмная Башня"))
                .andExpect(jsonPath("$[0].publisherYear").value(2005))
                .andExpect(jsonPath("$[0].genre").value("детектив"))
                .andExpect(jsonPath("$[0].publisher").value("АСТ"))
                .andExpect(jsonPath("$[0].countOfPage").value(856))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].author").value("Дэн Браун"))
                .andExpect(jsonPath("$[1].title").value("Ангелы и демоны"))
                .andExpect(jsonPath("$[1].publisherYear").value(2000))
                .andExpect(jsonPath("$[1].genre").value("детектив"))
                .andExpect(jsonPath("$[1].publisher").value("АСИ"))
                .andExpect(jsonPath("$[1].countOfPage").value(450));

    }

    public MockMultipartFile createFileToMultipartFileTest() {

        try {
            File file = new File("C:\\Users\\User\\IdeaProjects\\library-app\\src\\test\\resources\\books.csv");
            FileInputStream fis = new FileInputStream(file);
            return new MockMultipartFile(file.getName(), file.getName()
                    , ContentType.APPLICATION_OCTET_STREAM.toString(), IOUtils.toByteArray(fis));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Librarian createTestLibrarian(int id, String name, String surname
            , String phoneNumber, LocalDate dateOfBirth) {
        Librarian librarian = new Librarian(id, name, surname, phoneNumber, dateOfBirth);
        return libraryRepository.save(librarian);

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
