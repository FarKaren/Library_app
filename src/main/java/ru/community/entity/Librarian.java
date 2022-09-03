package ru.community.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "librarian")
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё]+", message = "Name don't match the format")
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё]+", message = "Surname don't match the format")
    @Column(name = "surname")
    private String surname;

    @NotEmpty
    @Pattern(regexp = "(8-?\\d{3}-?\\d{3}-?\\d{2}-?\\d{2})", message = "Number does not much to format")
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}
