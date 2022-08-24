package ru.community.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderEditDto {

    private String phoneNumber;
    private String email;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    public ReaderEditDto(String phoneNumber, LocalDate dateOfBirth) {

        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public ReaderEditDto(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public ReaderEditDto(LocalDate dateOfBirth, String email) {
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public ReaderEditDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ReaderEditDto(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
