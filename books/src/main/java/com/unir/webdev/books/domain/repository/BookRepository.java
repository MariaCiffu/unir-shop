package com.unir.webdev.books.domain.repository;

import com.unir.webdev.books.domain.Book;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository {

    List<Book> getAllProducts();
    List<Book> getAllProductsBy(String name, String author);
    Boolean isValidBook(UUID bookID);
    Either<Object, UUID> changeToUnavailability(UUID books);
    void changeAvailabilityOf(UUID book);
    boolean areAvailable(UUID book);
}
