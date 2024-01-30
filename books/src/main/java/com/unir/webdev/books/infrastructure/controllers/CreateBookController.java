package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.CreateBookUseCase;
import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.infrastructure.controllers.DTO.CreateBookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation (summary = "Create Book", description = "Create a new book with the " +
                                                       "provided data")
    @ApiResponses (value = {@ApiResponse (responseCode = "200", description =
            "Book " + "created " + "successfully", content = {@Content (mediaType = "application/json", schema = @Schema (implementation = String.class))}), @ApiResponse (responseCode = "500", description = "Internal Server Error, data unprocessable", content = @Content), @ApiResponse (responseCode = "422", description = "Unprocessable Entity, invalid data provided", content = @Content)})
    public ResponseEntity<?> handle(
            @io.swagger.v3.oas.annotations.parameters.RequestBody (description =
                                                                           "CreateBookRequest object", required = true, content = @Content (schema = @Schema (implementation = CreateBookRequest.class)))
            @RequestBody
            CreateBookRequest createBookRequest
                                   ) {
        return Option.of(createBookRequest)
                     .filter(CreateBookRequest :: validData)
                     .map(CreateBookController :: requestToDomain)
                     .map(createBookUseCase :: createBook)
                     .map(CreateBookController :: buildResponse)
                     .getOrElse(ResponseEntity.unprocessableEntity()
                                              .body("Invalid data"));
    }
}
