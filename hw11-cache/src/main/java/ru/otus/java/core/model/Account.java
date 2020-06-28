package ru.otus.java.core.model;

import lombok.*;
import ru.otus.java.core.annotation.Id;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private long no;
    private String type;
    private Integer rest;
}
