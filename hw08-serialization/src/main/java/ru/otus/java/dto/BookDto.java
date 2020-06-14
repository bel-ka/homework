package ru.otus.java.dto;

import lombok.*;

@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter@Getter
public class BookDto {
    private String title;
    private String author;
    private int pageCount;
    private boolean hasPicture;
    private double price;
    private int year;
}
