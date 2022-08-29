import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.Application;
import ru.community.dto.ReaderEditDto;
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

    @Test
    public void getReaderByIdTest() throws Exception {
        var reader = new Reader(1, "Аркадий", "Павлов", "8-999-124-45-54", "qwe@.fgk.ty",
                LocalDate.of(1954, 12, 12));
        repository.save(reader);

        this.mockMvc.perform(
                        get("/reader/{id}/me", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
//                .andExpect(jsonPath("name").value("Аркадий"));
    }

    @Test
    public void editReaderTest() throws Exception {
        var reader = new Reader(-6, "Григорий", "Архипов", "8-999-299-88-77", "qwerty@mail.ru",
                LocalDate.of(1985, 11, 25));
        repository.save(reader);

        var readerEditDto = ReaderEditDto.builder()
                .phoneNumber("8-999-299-88-11")
                .dateOfBirth(LocalDate.of(1985, 1, 25))
                .build();

        this.mockMvc.perform(
                        put("/reader/{id}/me/edit", -6)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(readerEditDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("phoneNumber").value("8-999-299-88-11"));
    }
}
