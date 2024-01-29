package com.unir.webdev.books.application;

import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.domain.repository.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateBookUseCase {
    BookRepository bookRepository;

    public Either<String, Book> createBook(Book book) {
        return Option.of(book)
                     .map(bookRepository :: createBook)
                     .getOrElse(() -> Either.left("Invalid Data"));
    }
}
