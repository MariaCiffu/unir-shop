package com.unir.webdev.books.application;

import com.unir.webdev.books.domain.events.BookEvents;
import com.unir.webdev.books.domain.repository.BookRepository;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestBookUseCase {
    BookEvents bookEvents;
    BookRepository bookRepository;

    public Either<String, Boolean> requestBooks(List<UUID> books) {
        var map = books.filter(bookRepository :: isValidBook)
                       .filter(bookRepository :: areAvailable)
                       .map(bookRepository :: changeToUnavailability)
                       .map(uuids -> uuids.isRight() ? uuids.get() : null);
        return map.isEmpty() ? Either.left("invalid Ids") : sendEvents(map);

    }


    private Either<String, Boolean> sendEvents(List<UUID> books) {
        return Try.of(() -> bookEvents.requestBooksCreation(books.asJava()))
                  .onFailure(throwable -> books.forEach(bookRepository :: changeAvailabilityOf))
                  .toEither("Send Event For Request Books Failed");
    }
}
