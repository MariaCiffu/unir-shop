package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.UpdateBookUseCase;
import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.infrastructure.controllers.DTO.UpdateBookRequest;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/books")
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateBookController {
    UpdateBookUseCase updateBookUseCase;

    @Contract ("_, _ -> new")
    @NotNull
    private static Book requestToDomain(
            @NotNull UpdateBookRequest updateBookRequest, UUID id
                                       ) {
        return Book.toUpdate(id, updateBookRequest.name(), updateBookRequest.isbn(),
                             updateBookRequest.image(), updateBookRequest.author(),
                             updateBookRequest.releaseYear(), updateBookRequest.rate(),
                             updateBookRequest.language(), updateBookRequest.available());
    }

    @NotNull
    private static ResponseEntity<String> buildResponse(@NotNull Either<String, Book> books) {
        return books.isLeft() ? ResponseEntity.badRequest()
                                              .body(books.getLeft())
                              : ResponseEntity.ok("Process done");
    }

    @PutMapping ("/{bookID}")
    public ResponseEntity<?> updateController(@RequestBody UpdateBookRequest updateBookRequest, @PathVariable UUID bookID) {
        return Option.of(updateBookRequest)
                     .filter(updateBookRequest1 -> updateBookRequest.validData())
                     .map(updateBookRequest1 -> requestToDomain(updateBookRequest, bookID))
                     .map(updateBookUseCase :: updateBook)
                     .map(UpdateBookController :: buildResponse)
                     .getOrElse(() -> ResponseEntity.badRequest()
                                                    .body("Invalid Request Data"));
    }
}
