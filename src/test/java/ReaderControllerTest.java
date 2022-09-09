import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.Application;
import ru.community.entity.Reader;
import ru.community.repository.ReaderRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ReaderRepository repository;

    @BeforeEach
    public void deleteAll() {
        repository.deleteAll();
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
}
