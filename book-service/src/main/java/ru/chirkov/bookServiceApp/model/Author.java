package ru.chirkov.bookServiceApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Author", schema = "PUBLIC")
@Getter
@Setter
//@Access(AccessType.FIELD)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @JsonBackReference
    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY,
            targetEntity = Book.class)
    @Column(name = "books")
    private List<Book> books;

}
