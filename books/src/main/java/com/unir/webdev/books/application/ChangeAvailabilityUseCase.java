package com.unir.webdev.books.application;

import com.unir.webdev.books.domain.repository.BookRepository;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class ChangeAvailabilityUseCase {
    BookRepository bookRepository;

    public Either<String, Boolean> changeAvailability(List<UUID> booksID) {
        return List.ofAll(booksID)
                   .filter(bookRepository :: isValidBook)
                   .map(this :: changeAvailabilityOf)
                   .foldLeft(Either.right(true), validateNotErrorAtProcess());


    }

    @NotNull
    private static BiFunction<Either<String, Boolean>, Either<String, Void>, Either<String, Boolean>> validateNotErrorAtProcess() {
        return (acc, either) -> acc.isLeft() ? acc : either.isLeft() ? Either.left(either.getLeft()) : acc;
    }

    private @NotNull Either<String, Void> changeAvailabilityOf(UUID uuid) {
        return Try.run(() -> bookRepository.changeAvailabilityOf(uuid))
                  .toEither("Availability do not changed");
    }
}
