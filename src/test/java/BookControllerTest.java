import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.entity.Book;
import ru.community.entity.Genre;
import ru.community.exception.BookNotFound;
import ru.community.repository.BookRepository;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class BookControllerTest {

    private ObjectMapper objectMapper;

    private BookRepository bookRepository;

    private MockMvc mockMvc;



    @Test
    public void getBookByIdTest() throws Exception {
        Book book = createTestBook(-1, "Джон Толкиен", "Хоббит", 1973,
                                                       Genre.NOVEL, "George Allen & Unwin", 208);

        this.mockMvc.perform(
                get("/book/{id}", -1)
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


    }

    private Book createTestBook(int id, String author, String title, int publisherYear,
                                Genre genre, String publisher, int countOfPage) {
        Book book = new Book(id, author, title, publisherYear, genre, publisher, countOfPage);
        return bookRepository.save(book);
    }

    @Test
    public void getAllBooks() throws Exception {
        Book book1 = createTestBook(-1, "Джон Толкиен", "Хоббит", 1973
                                                       ,Genre.NOVEL, "George Allen & Unwin", 208);
        Book book2 = createTestBook(-2, "Борис Акунин", "Турецкий Гамбит", 1972
                                                                     ,Genre.DETECTIVE, "ACT", 450);
        this.mockMvc.perform(get("/book/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(book1,book2))));

    }
}
