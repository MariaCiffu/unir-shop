package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.ChangeAvailabilityUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.ChangeAvailabilityRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/books")
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class ChangeAvailabilityBookController {
    ChangeAvailabilityUseCase changeAvailabilityUseCase;

    @NotNull
    private static ResponseEntity<String> buildResponse(Either<String, Boolean> booleans) {
        return booleans.isLeft() ? ResponseEntity.unprocessableEntity()
                                                 .body(booleans.getLeft())
                                 : ResponseEntity.ok()
                                                 .body("Availability Changed");
    }

    @PatchMapping ("/availability")
    @Operation (summary = "Change Book Availability", description = "Change the " +
                                                                    "availability " +
                                                                    "status for a list "
                                                                    + "of books")
    @ApiResponses (value = {@ApiResponse (responseCode = "200", description =
            "Availability Changed Successfully", content = {@Content (mediaType = "application/json", schema = @Schema (implementation = String.class))}), @ApiResponse (responseCode = "422", description = "Unprocessable Entity, validation failed", content = @Content), @ApiResponse (responseCode = "400", description = "Bad Request, invalid data provided", content = @Content)})
    public ResponseEntity<?> handle(
            @io.swagger.v3.oas.annotations.parameters.RequestBody (description =
                                                                           "ChangeAvailabilityRequest object", required = true, content = @Content (schema = @Schema (implementation = ChangeAvailabilityRequest.class)))
            @RequestBody
            ChangeAvailabilityRequest changeAvailabilityRequest
                                   ) {
        return Option.of(changeAvailabilityRequest)
                     .filter(ChangeAvailabilityRequest :: existBooks)
                     .map(ChangeAvailabilityRequest :: booksID)
                     .map(List :: ofAll)
                     .map(changeAvailabilityUseCase :: changeAvailability)
                     .map(ChangeAvailabilityBookController :: buildResponse)
                     .getOrElse(ResponseEntity.badRequest()
                                              .body("Invalid Data Given"));

    }


}
