package com.unir.webdev.books.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unir.webdev.books.domain.valueObjects.Author;
import com.unir.webdev.books.domain.valueObjects.Available;
import com.unir.webdev.books.domain.valueObjects.BookName;
import com.unir.webdev.books.domain.valueObjects.Image;
import com.unir.webdev.books.domain.valueObjects.Isbn;
import com.unir.webdev.books.domain.valueObjects.Language;
import com.unir.webdev.books.domain.valueObjects.Rate;
import com.unir.webdev.books.domain.valueObjects.ReleaseYear;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Setter
@Accessors (chain = true, fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class Book {
    @JsonProperty
    UUID bookId;
    @JsonProperty
    BookName bookName;
    @JsonProperty
    Isbn isbn;
    @JsonProperty
    Image image;
    @JsonProperty
    Author author;
    @JsonProperty
    ReleaseYear releaseYear;
    @JsonProperty
    Rate rate;
    @JsonProperty
    Language language;
    @JsonProperty
    Available available;

    @Contract ("_, _, _, _, _, _, _, _ -> new")
    static public @NotNull Book of(
            @NonNull String bookName, @NonNull String isbn, @NonNull String image,
            @NonNull String author, @NonNull Integer releaseYear, @NonNull Double rate,
            @NonNull String language, @NonNull Boolean available
                                  ) {
        return new Book(UUID.randomUUID(), new BookName(bookName), new Isbn(isbn),
                        new Image(image), new Author(author),
                        new ReleaseYear(releaseYear), new Rate(rate),
                        new Language(language), new Available(available));
    }

    @Contract ("_, _, _, _, _, _, _, _, _ -> new")
    static public @NotNull Book toUpdate(
            @NonNull UUID id, @NonNull String bookName, @NonNull String isbn,
            @NonNull String image, @NonNull String author, @NonNull Integer releaseYear
            , @NonNull Double rate, @NonNull String language, @NonNull Boolean available
                                        ) {
        return new Book(id, new BookName(bookName), new Isbn(isbn), new Image(image),
                        new Author(author), new ReleaseYear(releaseYear),
                        new Rate(rate), new Language(language), new Available(available));
    }

    public Book makeUnavailable() {
        this.available = new Available(false);
        return this;
    }

    public Book updateName(Book book) {
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
