package ru.otus.java;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.dto.BookDto;
import ru.otus.java.dto.BookSeriesDto;
import ru.otus.java.mapper.JsonFormatter;

public class DemoJson {
    private static final Logger logger = LoggerFactory.getLogger(DemoJson.class);

    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonFormatter jsonFormatter = new JsonFormatter();
        BookDto book1 = BookDto.builder()
                .author("Stephen King")
                .title("The Gunslinger")
                .year(2012)
                .pageCount(320)
                .price(286.90)
                .hasPicture(true)
                .build();
        BookDto book2 = BookDto.builder()
                .author("Stephen King")
                .title("The Drawing of the Three")
                .year(1987)
                .pageCount(400)
                .price(179)
                .hasPicture(false)
                .build();
        BookSeriesDto bookSeries = new BookSeriesDto("The Dark Tower");
        bookSeries.addBook(book1);
        bookSeries.addBook(book2);
        String bookSeriesToMyGson = jsonFormatter.toJson(bookSeries);
        String bookSeriesToGson = gson.toJson(bookSeries);
        logger.info("my toJson: " + bookSeriesToMyGson);
        logger.info("gson toJson: " + bookSeriesToGson);
        BookSeriesDto bookSeriesFromGson = gson.fromJson(bookSeriesToMyGson, BookSeriesDto.class);
        logger.info("from my json: " + bookSeriesFromGson.toString());
        logger.info(String.valueOf(bookSeries.equals(bookSeriesFromGson)));
    }
}
