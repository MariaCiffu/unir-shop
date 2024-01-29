package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.RequestBookUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.BooksIdVerificationRequest;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("api/v1/books")
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class RequestBooksController {
    RequestBookUseCase requestBookUseCase;

    @NotNull
    private static ResponseEntity<String> buildResponse(@NotNull Either<String, Boolean> booleans) {
        return booleans.isLeft() ? ResponseEntity.internalServerError()
                                                 .body(booleans.getLeft())
                                 : ResponseEntity.ok("Request successfully");
    }

    @PostMapping ("/request")
    public ResponseEntity<?> createRequest(@RequestBody BooksIdVerificationRequest booksIdVerificationRequest) {
        return Option.of(booksIdVerificationRequest)
                     .filter(BooksIdVerificationRequest :: isNotNullBooksID)
                     .map(BooksIdVerificationRequest :: booksID)
                     .map(List :: ofAll)
                     .map(booksId -> requestBookUseCase.requestBooks(booksId))
                     .map(RequestBooksController :: buildResponse)
                     .getOrElse(ResponseEntity.badRequest()
                                              .body("invalid data"));
    }

}
