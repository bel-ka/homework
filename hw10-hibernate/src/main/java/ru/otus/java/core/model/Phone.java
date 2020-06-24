package ru.otus.java.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "number")
    private String number;
}
