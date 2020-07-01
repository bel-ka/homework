package ru.otus.java.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public User(String name, String login, String password){
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
