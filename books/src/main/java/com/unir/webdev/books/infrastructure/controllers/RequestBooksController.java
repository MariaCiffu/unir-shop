package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.RequestBookUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.BooksIdVerificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private static ResponseEntity<String> buildResponse(@NotNull Either<String,
            Boolean> booleans) {
        return booleans.isLeft() ? ResponseEntity.internalServerError()
                                                 .body(booleans.getLeft())
                                 : ResponseEntity.ok("Request successfully");
    }

    @PostMapping ("/request")
    @Operation (summary = "Request Books", description = "Request one or more books by " +
                                                         "providing their IDs")
    @ApiResponses (value = {@ApiResponse (responseCode = "200", description = "Books " +
                                                                              "requested successfully", content = {@Content (mediaType = "application/json", schema = @Schema (implementation = String.class))}), @ApiResponse (responseCode = "500", description = "Internal Server Error, the request could not be processed", content = @Content), @ApiResponse (responseCode = "400", description = "Bad Request, invalid data provided", content = @Content)})
    public ResponseEntity<?> createRequest(
            @io.swagger.v3.oas.annotations.parameters.RequestBody (description =
                                                                           "BooksIdVerificationRequest object containing the list of book IDs to request", required = true, content = @Content (schema = @Schema (implementation = BooksIdVerificationRequest.class)))
            @RequestBody
            BooksIdVerificationRequest booksIdVerificationRequest
                                          ) {
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
