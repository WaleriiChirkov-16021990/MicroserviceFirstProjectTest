package ru.chirkov.readerServiceApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name = "Reader", schema = "PUBLIC")
@Getter
@Setter
public class Reader implements Serializable {
    @Serial
    private static final long serialVersionUID = 7771028347191988404L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

}
