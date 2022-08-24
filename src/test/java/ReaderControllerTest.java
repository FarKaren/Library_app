import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.community.Application;
import ru.community.controller.ReaderController;
import ru.community.dto.ReaderEditDto;
import ru.community.entity.Reader;
import ru.community.exception.ReaderNotFound;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReaderController readerController;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getReaderByIdTest() throws Exception {
        var reader = new Reader(-5, "Аркадий", "Павлов", "8-999-124-45-54", "qwe@.fgk.ty",
                LocalDate.of(1954, 12, 12));
        this.mockMvc.perform(
                        get("/reader/{id}/me", -5)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.mapper.writeValueAsString(reader)))
                .andExpect(status().isOk());

        assertEquals(readerController.getReader(-5), reader);
        assertThrows(ReaderNotFound.class, () -> {
            readerController.getReader(-10);
        }, "Reader not found");
    }

    @Test
    public void editReaderTest() throws Exception {
        var reader = new Reader(-6, "Григорий", "Архипов", "8-999-299-88-77", "qwerty@mail.ru",
                LocalDate.of(1985, 11, 25));
        var readerEditDto = new ReaderEditDto("8-999-299-88-77", "qwerty@mail.ru",
                LocalDate.of(1985, 11, 25));
        this.mockMvc.perform(
                        put("/reader/{id}/me/edit", -6)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.mapper.writeValueAsString(reader)))
                .andExpect(status().isOk());

        assertEquals(readerController.editReader(-6, readerEditDto), ResponseEntity.ok(reader));
    }
}
