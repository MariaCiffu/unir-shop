package com.unir.webdev.books.infrastructure.repositories;

import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.domain.repository.BookRepository;
import com.unir.webdev.books.infrastructure.persistence.BookRepositoryJPA;
import com.unir.webdev.books.infrastructure.persistence.entity.BookEntity;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Available;
import com.unir.webdev.books.infrastructure.persistence.filter.BookSpec;
import com.unir.webdev.books.infrastructure.persistence.mappers.BookMapper;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@FieldDefaults (makeFinal = true, level = AccessLevel.PRIVATE)
public class BookRepositoryImp implements BookRepository {
    BookRepositoryJPA bookRepositoryJPA;
    BookSpec bookSpec;

    @Override
    public List<Book> getAllBooks() {
        return bookRepositoryJPA.findAll()
                                .stream()
                                .map(BookMapper :: fromDbToDomain)
                                .toList();
    }

    @Override
    public List<Book> getBooksBy(String name, String author) {
        return bookRepositoryJPA.findAll(bookSpec.filterColumns(name, author))
                                .stream()
                                .map(BookMapper :: fromDbToDomain)
                                .toList();
    }

    @Override
    public Boolean isValidBook(UUID bookID) {
        return bookRepositoryJPA.existsById(bookID);
    }

    @Override
    public Either<String, UUID> changeToUnavailability(UUID book) {
        return Try.of(() -> Optional.of(book)
                                    .flatMap(bookRepositoryJPA :: findById)
                                    .map(BookEntity :: makeUnavailable)
                                    .map(bookRepositoryJPA :: save)
                                    .map(BookEntity :: bookId)
                                    .get())
                  .toEither("Not changed Availability at DB");
    }

    @Override
    public UUID changeAvailabilityOf(UUID book) {
        return Optional.of(book)
                       .flatMap(bookRepositoryJPA :: findById)
                       .map(BookEntity :: makeAvailable)
                       .map(bookRepositoryJPA :: save)
                       .map(BookEntity :: bookId)
                       .orElseThrow(IllegalStateException :: new);
    }

    @Override
    public Either<String, Book> updateBook(Book book) {
        return Try.of(() -> bookRepositoryJPA.findById(book.bookId())
                                             .get())
                  .map(bookEntity -> bookEntity.updateEntity(BookMapper.fromDomainToDb(book)))
                  .map(bookRepositoryJPA :: save)
                  .map(BookMapper :: fromDbToDomain)
                  .toEither("Not update possible at DB");
    }

    @Override
    public Either<String, Book> createBook(Book book) {
        return Try.of(() -> bookRepositoryJPA.save(BookMapper.fromDomainToDb(book)))
                  .map(BookMapper :: fromDbToDomain)
                  .toEither("Error to save at DB");
    }


    @Override
    public boolean areAvailable(UUID bookID) {
        return Optional.ofNullable(bookID)
                       .map(bookRepositoryJPA :: findById)
                       .flatMap(bookEntity -> bookEntity.map(BookEntity :: available))
                       .map(Available :: available)
                       .orElse(false);

    }
}
