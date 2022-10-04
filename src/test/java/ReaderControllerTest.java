import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.Application;
import ru.community.entity.*;
import ru.community.repository.BookBindingRepository;
import ru.community.repository.BookRepository;
import ru.community.repository.LibraryDepartmentRepository;
import ru.community.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ReaderRepository repository;

    @Autowired
    private BookBindingRepository bookBindingRepository;
    @Autowired
    private LibraryDepartmentRepository libraryDepartmentRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void deleteAll() {
        repository.deleteAll();
        bookBindingRepository.deleteAll();
        libraryDepartmentRepository.deleteAll();
        bookRepository.deleteAll();
    }


    @Test
    public void getReaderByIdTest() throws Exception {
        var reader = new Reader(2, "Аркадий", "Павлов", "8-999-124-45-54", "qwe@.fgk.ty",
                LocalDate.of(1954, 12, 12));
        repository.save(reader);

        this.mockMvc.perform(
                        get("/reader/{id}/me", 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(2))
                .andExpect(jsonPath("name").value("Аркадий"));
    }

    @Test
    public void editReaderTest() throws Exception {
        var reader = new Reader(1, "Григорий", "Архипов", "8-999-299-88-77", "qwerty@mail.ru",
                LocalDate.of(1985, 11, 25));
        repository.save(reader);

        var readerEdit = new Reader(1, "Александр", "Петров", "8-555-555-55-55", "qwerty@gmail.com",
                LocalDate.of(1999, 1, 15));

        this.mockMvc.perform(
                        put("/reader/{id}/me/edit", reader.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(readerEdit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("phoneNumber").value("8-555-555-55-55"))
                .andExpect(jsonPath("name").value("Григорий"))
                .andExpect(jsonPath("surname").value("Архипов"));
    }

    @Test
    public void getMyBooksByStatusTest() throws Exception {
        var reader1 = new Reader(1, "Аркадий", "Павлов", "8-999-124-45-54", "qwe@.fgk.ty",
                LocalDate.of(1954, 12, 12));
        var reader2 = new Reader(2, "Александр", "Петров", "8-555-555-55-55", "qwerty@gmail.com",
                LocalDate.of(1999, 1, 15));
        var readers = List.of(reader1, reader2);
        repository.saveAll(readers);

        var book1 = new Book(1, "Джон Толкиен", "Хоббит", 1973
                , Genre.NOVEL, "George Allen & Unwin", 208);
        var book2 = new Book(2, "Борис Акунин", "Турецкий Гамбит", 1972
                , Genre.DETECTIVE, "ACT", 450);
        var book3 = new Book(3, "Терри Пратчет", "Цвет волшебства", 2004
                , Genre.FANTASY, "ЭКСМО", 366);
        var books = List.of(book1, book2, book3);
        bookRepository.saveAll(books);
        var libraryDepartment = new LibraryDepartment(1, "Филиал №1", "ул. Пушкина, 10");
        libraryDepartmentRepository.save(libraryDepartment);

        var bookBinding1 = new BookBinding(book1, reader1, LocalDate.of(2022, 9, 12),
                LocalDate.of(2022, 9, 26), Status.ACTUAL, libraryDepartment);
        var bookBinding2 = new BookBinding(book2, reader1, LocalDate.of(2022, 8, 12),
                LocalDate.of(2022, 8, 26), Status.RETURNED, libraryDepartment);
        var bookBinding3 = new BookBinding(book3, reader2, LocalDate.of(2022, 8, 30),
                LocalDate.of(2022, 9, 12), Status.EXPIRED, libraryDepartment);
        var bookBindings = List.of(bookBinding1, bookBinding2, bookBinding3);
        bookBindingRepository.saveAll(bookBindings);

        this.mockMvc.perform(
                        get("/reader/{id}/myBooks/", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("status", "ACTUAL")
                                .param("status", "RETURNED"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookBinding1, bookBinding2))));
    }

    @Test
    public void getRecommendedBooksByGenreTest() throws Exception {
        var reader1 = new Reader(1, "Аркадий", "Павлов", "8-999-124-45-54", "qwe@.fgk.ty",
                LocalDate.of(1954, 12, 12));
        var reader2 = new Reader(2, "Александр", "Петров", "8-555-555-55-55", "qwerty@gmail.com",
                LocalDate.of(1999, 1, 15));
        var readers = List.of(reader1, reader2);
        repository.saveAll(readers);

        var book1 = new Book(1, "Джон Толкиен", "Хоббит", 1973
                , Genre.NOVEL, "George Allen & Unwin", 208);
        var book2 = new Book(2, "Борис Акунин", "Турецкий Гамбит", 1972
                , Genre.DETECTIVE, "ACT", 450);
        var book3 = new Book(3, "Терри Пратчет", "Цвет волшебства", 2004
                , Genre.FANTASY, "ЭКСМО", 366);
        var book4 = new Book(4, "Ник Перумов", "Рождение мага", 1999
                , Genre.FANTASY, "ЭКСМО", 398);
        var book5 = new Book(5, "Артур Конан Дойл", "Шерлок Холмс", 1989
                , Genre.DETECTIVE, "ACT", 450);
        var book6 = new Book(6, "Сергей Лукьяненко", "Черновик", 2006
                , Genre.FANTASY, "ЭКСМО", 366);
        var books = List.of(book1, book2, book3, book4, book5, book6);
        bookRepository.saveAll(books);
        var libraryDepartment = new LibraryDepartment(1, "Филиал №1", "ул. Пушкина, 10");
        libraryDepartmentRepository.save(libraryDepartment);

        var bookBinding3 = new BookBinding(book3, reader1, LocalDate.of(2022, 8, 30),
                LocalDate.of(2022, 9, 12), Status.EXPIRED, libraryDepartment);
        var bookBinding4 = new BookBinding(book4, reader1, LocalDate.of(2022, 8, 30),
                LocalDate.of(2022, 9, 12), Status.EXPIRED, libraryDepartment);
        var bookBinding5 = new BookBinding(book5, reader1, LocalDate.of(2022, 8, 30),
                LocalDate.of(2022, 9, 12), Status.EXPIRED, libraryDepartment);
        var bookBindings = List.of(bookBinding3, bookBinding4, bookBinding5);
        bookBindingRepository.saveAll(bookBindings);

        this.mockMvc.perform(
                        get("/reader/{id}/recommend/", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(book6, book2))));
    }
}
