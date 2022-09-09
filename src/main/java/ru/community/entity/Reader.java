package ru.community.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reader")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Pattern(regexp = "[А-Яа-я]+", message = "Name does not much to format")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "[А-Яа-я]+", message = "Surname does not much to format")
    @Column(name = "surname")
    private String surname;

    @Pattern(regexp = "(8-?\\d{3}-?\\d{3}-?\\d{2}-?\\d{2})", message = "Number does not much to format")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Pattern(regexp = "(.*@.*\\.[A-za-z]{1,5})", message = "Email does not much to format")
    @Column(name = "email")
    private String email;


    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;



}
