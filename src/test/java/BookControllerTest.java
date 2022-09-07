import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.Application;
import ru.community.entity.Book;
import ru.community.entity.Genre;
import ru.community.exception.BookNotFound;
import ru.community.repository.BookRepository;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBookByIdTest() throws Exception {
        createTestBook(1, "Джон Толкиен", "Хоббит", 1973, Genre.NOVEL,
                "George Allen & Unwin", 208);

        this.mockMvc.perform(
                        get("/book/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("author").value("Джон Толкиен"))
                .andExpect(jsonPath("title").value("Хоббит"))
                .andExpect(jsonPath("publisherYear").value(1973))
                .andExpect(jsonPath("genre").value("роман"))
                .andExpect(jsonPath("publisher").value("George Allen & Unwin"))
                .andExpect(jsonPath("countOfPage").value(208));
    }


    @Test
    public void getAllBooks() throws Exception {
        Book book1 = createTestBook(1, "Джон Толкиен", "Хоббит", 1973
                , Genre.NOVEL, "George Allen & Unwin", 208);
        Book book2 = createTestBook(2, "Борис Акунин", "Турецкий Гамбит", 1972
                , Genre.DETECTIVE, "ACT", 450);
        this.mockMvc.perform(get("/book/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(book1, book2))));
    }

    @Test
    public void getBooksException() throws Exception {

        this.mockMvc.perform(get("/book/{id}", 5)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), BookNotFound.class));
    }

    private Book createTestBook(int id, String author, String title, int publisherYear,
                                Genre genre, String publisher, int countOfPage) {
        Book book = new Book(id, author, title, publisherYear, genre, publisher, countOfPage);
        return bookRepository.save(book);
    }


}
