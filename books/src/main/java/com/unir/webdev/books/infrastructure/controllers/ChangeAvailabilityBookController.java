package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.ChangeAvailabilityUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.ChangeAvailabilityRequest;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/books")
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class ChangeAvailabilityBookController {
    ChangeAvailabilityUseCase changeAvailabilityUseCase;

    @PostMapping ("/changeAvailability")
    public ResponseEntity<?> handle(@RequestBody ChangeAvailabilityRequest changeAvailabilityRequest) {
        return Option.of(changeAvailabilityRequest)
                     .filter(ChangeAvailabilityRequest :: existBooks)
                     .map(ChangeAvailabilityRequest :: booksID)
                     .map(uuids -> List.ofAll(uuids))
                     .map(changeAvailabilityUseCase :: changeAvailability)
                     .map(booleans -> booleans.isLeft() ? ResponseEntity.badRequest()
                                                                        .body(booleans.getLeft()) : ResponseEntity.ok("availability changed"))
                     .get();

    }
}
