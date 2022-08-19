package ru.community.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "library_department")
public class LibraryDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String address;

}
