package ru.otus.java.dto;

import lombok.*;

@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @Setter@Getter
    private String title;
    @Setter@Getter
    private String author;
    @Setter@Getter
    private int pageCount;
    @Setter@Getter
    private boolean hasPicture;
    @Setter@Getter
    private double price;
    @Setter@Getter
    private int year;
}
