package ru.chirkov.bookServiceApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Book", schema = "PUBLIC")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Author.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
//
//    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER)
//    private Set<ReaderEntity> readers;
}
