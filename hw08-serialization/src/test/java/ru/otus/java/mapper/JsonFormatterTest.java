package ru.otus.java.mapper;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.java.dto.AddressDTO;
import ru.otus.java.dto.BookDto;
import ru.otus.java.dto.BookSeriesDto;
import ru.otus.java.dto.PersonDTO;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JsonFormatterTest {
    private JsonFormatter jsonFormatter;

    @BeforeEach
    void setUp() {
        jsonFormatter = mock(JsonFormatter.class);
    }

    @Test
    void shouldReturnNullIfInputObjectIsNull(){
        when(jsonFormatter.toJson(null)).thenReturn(null);
    }

    @Test
    void shouldWorkWithPrimitiveType(){
        BookDto book1 = BookDto.builder()
                .title("The Gunslinger")
                .year(2012)
                .pageCount(320)
                .price(286.90)
                .hasPicture(true)
                .build();
        Gson gson = new Gson();
        String bookSeriesToGson = gson.toJson(book1);
        when(jsonFormatter.toJson(book1)).thenReturn(bookSeriesToGson);
    }

    @Test
    void shouldWorkWithCollection(){
        BookDto book2 = BookDto.builder()
                .author("Stephen King")
                .title("The Drawing of the Three")
                .year(1987)
                .pageCount(400)
                .price(179)
                .hasPicture(false)
                .build();
        BookDto book1 = BookDto.builder()
                .author("Stephen King")
                .title("The Gunslinger")
                .year(2012)
                .pageCount(320)
                .price(286.90)
                .hasPicture(true)
                .build();
        BookSeriesDto bookSeries = new BookSeriesDto("The Dark Tower");
        bookSeries.addBook(book1);
        bookSeries.addBook(book2);
        Gson gson = new Gson();
        String bookSeriesToGson = gson.toJson(bookSeries);
        when(jsonFormatter.toJson(bookSeries)).thenReturn(bookSeriesToGson);
    }

    @Test
    void shouldWorkWithObject(){
        AddressDTO address = AddressDTO.builder()
                .city("Moscow")
                .country("Russia")
                .build();
        PersonDTO person = PersonDTO.builder()
                .personId(new Date().getTime())
                .firstName("Ivanov")
                .lastName("Petr")
                .address(address)
                .build();
        Gson gson = new Gson();
        String personToGson = gson.toJson(person);
        when(jsonFormatter.toJson(person)).thenReturn(personToGson);
    }
}