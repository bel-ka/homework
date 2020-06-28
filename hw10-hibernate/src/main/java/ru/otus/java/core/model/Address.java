package ru.otus.java.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address")
@ToString
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "street")
    private String street;
}
