package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.IdVerificationUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.BooksIdVerificationRequest;
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

    @PostMapping ("/verify")
    public ResponseEntity<Boolean> verifyIds(@RequestBody BooksIdVerificationRequest booksIdVerificationRequest) {
        return Optional.ofNullable(booksIdVerificationRequest)
                       .filter(BooksIdVerificationRequest :: isNotNullBooksID)
                       .map(BooksIdVerificationRequest :: booksID)
                       .map(idVerificationUseCase :: verify)
                       .map(ResponseEntity :: ok)
                       .orElse(ResponseEntity.badRequest()
                                             .body(false));
    }
}
