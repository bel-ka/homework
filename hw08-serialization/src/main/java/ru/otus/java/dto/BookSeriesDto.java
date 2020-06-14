package ru.otus.java.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@EqualsAndHashCode
public class BookSeriesDto {
    @Setter@Getter
    private String name;
    @Getter
    private final ArrayList<BookDto> books = new ArrayList<>();

    public BookSeriesDto(String name) {
        this.name = name;
    }

    public void addBook(BookDto book){
        books.add(book);
    }
}
