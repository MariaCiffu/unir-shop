package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.IdVerificationUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.BooksIdVerificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping ("api/v1/books")
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class VerifyBooksIDController {
    IdVerificationUseCase idVerificationUseCase;

    @PostMapping("/verify")
    @Operation (summary = "Verify Book IDs", description = "Verify the existence of book IDs in the system")
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "IDs verified successfully, true if all exist, false otherwise",
                          content = { @Content (mediaType = "application/json",
                                                schema = @Schema (implementation = Boolean.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request, invalid data provided",
                         content = @Content)
    })
    public ResponseEntity<Boolean> verifyIds(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "BooksIdVerificationRequest object containing the list of book IDs to verify",
                                                                  required = true, content = @Content(schema = @Schema(implementation = BooksIdVerificationRequest.class)))
            @RequestBody BooksIdVerificationRequest booksIdVerificationRequest
                                            ) {
        return Optional.ofNullable(booksIdVerificationRequest)
                       .filter(BooksIdVerificationRequest :: isNotNullBooksID)
                       .map(BooksIdVerificationRequest :: booksID)
                       .map(idVerificationUseCase :: verify)
                       .map(ResponseEntity :: ok)
                       .orElse(ResponseEntity.badRequest()
                                             .body(false));
    }
}
