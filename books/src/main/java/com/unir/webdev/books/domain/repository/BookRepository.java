package com.unir.webdev.books.domain.repository;

import com.unir.webdev.books.domain.Book;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository {

    List<Book> getAllBooks();

    List<Book> getBooksBy(String name, String author);

    Boolean isValidBook(UUID bookID);

    Either<String, UUID> changeToUnavailability(UUID books);

    UUID changeAvailabilityOf(UUID book);

    Either<String, Book> updateBook(Book book);

    Either<String, Book> createBook(Book book);

    boolean areAvailable(UUID book);
}
