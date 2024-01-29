package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.CreateBookUseCase;
import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.infrastructure.controllers.DTO.CreateBookRequest;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/books")
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateBookController {
    CreateBookUseCase createBookUseCase;

    @NotNull
    private static ResponseEntity<String> buildResponse(Either<String, Book> books) {
        return books.isLeft() ? ResponseEntity.internalServerError()
                                              .body("Data " + "unprocessable")
                              : ResponseEntity.ok()
                                              .body("book created");
    }

    @NotNull
    private static Book requestToDomain(CreateBookRequest validRequest) {
        return Book.of(validRequest.name(), validRequest.isbn(), validRequest.image(),
                       validRequest.author(), validRequest.releaseYear(),
                       validRequest.rate(), validRequest.language(), true);
    }

    @PostMapping ("")
    public ResponseEntity<?> handle(@RequestBody CreateBookRequest createBookRequest) {
        return Option.of(createBookRequest)
                     .filter(CreateBookRequest :: validData)
                     .map(CreateBookController :: requestToDomain)
                     .map(createBookUseCase :: createBook)
                     .map(CreateBookController :: buildResponse)
                     .getOrElse(ResponseEntity.unprocessableEntity()
                                              .body("Invalid data"));
    }
}
