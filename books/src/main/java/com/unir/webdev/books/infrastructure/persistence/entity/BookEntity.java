package com.unir.webdev.books.infrastructure.persistence.entity;

import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Author;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Available;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.BookName;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Image;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Isbn;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Language;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.Rate;
import com.unir.webdev.books.infrastructure.persistence.entity.valueObjects.ReleaseYear;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors (fluent = true)
@FieldDefaults (level = AccessLevel.PRIVATE)
@Entity
public class BookEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    UUID bookId;

    @Embedded
    BookName bookName;

    @Embedded
    Isbn isbn;

    @Embedded
    Image image;

    @Embedded
    Author author;

    @Embedded
    ReleaseYear releaseYear;

    @Embedded
    Rate rate;

    @Embedded
    Language language;

    @Embedded
    Available available;

    public BookEntity makeUnavailable() {
        this.available = new Available(false);
        return this;
    }

    public BookEntity makeAvailable() {
        this.available = new Available(true);
        return this;
    }

    public BookEntity updateEntity(BookEntity book) {
        this.bookName = new BookName(book.bookName.bookName());
        this.isbn = new Isbn(book.isbn.isbn());
        this.image = new Image(book.image.url());
        this.author = new Author(book.author.author());
        this.releaseYear = new ReleaseYear(book.releaseYear.releaseYear());
        this.rate = new Rate(book.rate.rate());
        this.language = new Language(book.language.language());
        this.available = new Available(book.available.available());
        return this;
    }
}
