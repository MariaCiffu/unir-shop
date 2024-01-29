package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.ChangeAvailabilityUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.ChangeAvailabilityRequest;
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
    public ResponseEntity<?> handle(@RequestBody ChangeAvailabilityRequest changeAvailabilityRequest) {
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
