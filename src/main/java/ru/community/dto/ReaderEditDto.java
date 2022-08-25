package ru.community.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class ReaderEditDto {

    private String phoneNumber;
    private String email;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

}
