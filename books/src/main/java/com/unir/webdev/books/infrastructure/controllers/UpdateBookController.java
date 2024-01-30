package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.UpdateBookUseCase;
import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.infrastructure.controllers.DTO.UpdateBookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation (summary = "Update Book", description = "Update the details of an " +
                                                       "existing book by its ID")
    @ApiResponses (value = {@ApiResponse (responseCode = "200", description = "Book " +
                                                                              "updated " +
                                                                              "successfully", content = {@Content (mediaType = "application/json", schema = @Schema (implementation = String.class))}), @ApiResponse (responseCode = "400", description = "Bad Request, invalid data provided", content = @Content)})
    public ResponseEntity<?> updateController(
            @io.swagger.v3.oas.annotations.parameters.RequestBody (description =
                                                                           "UpdateBookRequest object containing the new details of the book", required = true, content = @Content (schema = @Schema (implementation = UpdateBookRequest.class)))
            @RequestBody
            UpdateBookRequest updateBookRequest,
            @Parameter (description = "UUID of the book to be updated", required = true)
            @PathVariable
            UUID bookID
                                             ) {
        return Option.of(updateBookRequest)
                     .filter(updateBookRequest1 -> updateBookRequest.validData())
                     .map(updateBookRequest1 -> requestToDomain(updateBookRequest,
                                                                bookID))
                     .map(updateBookUseCase :: updateBook)
                     .map(UpdateBookController :: buildResponse)
                     .getOrElse(() -> ResponseEntity.badRequest()
                                                    .body("Invalid Request Data"));
    }
}
