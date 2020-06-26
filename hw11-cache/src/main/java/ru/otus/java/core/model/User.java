package ru.otus.java.core.model;

import ru.otus.java.core.annotation.Id;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private long id;
    private String name;
    private int age;
}
