package com.unir.webdev.books.application;

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
public class ChangeAvailabilityUseCase {
    BookRepository bookRepository;

    public Either<String, Boolean> changeAvailability(List<UUID> booksID) {
        return booksID.filter(bookRepository :: isValidBook)
                      .map(this :: changeAvailabilityOf)
                      .map(either -> either.map(uuid -> true))
                      .getOrElse(Either.left("unknown ids"));
    }

    private Either<String, UUID> changeAvailabilityOf(UUID uuid) {
        return Try.of(() -> bookRepository.changeAvailabilityOf(uuid))
                  .toEither("Availability do not changed");
    }
}
