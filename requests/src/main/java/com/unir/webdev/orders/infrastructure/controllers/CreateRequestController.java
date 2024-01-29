package com.unir.webdev.orders.infrastructure.controllers;

import com.unir.webdev.orders.application.RegisterNewRequestUseCase;
import com.unir.webdev.orders.domain.response.Result;
import com.unir.webdev.orders.infrastructure.controllers.dto.RequestCreation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping ("/api/v1/requests")
@RequiredArgsConstructor
@FieldDefaults (makeFinal = true, level = AccessLevel.PRIVATE)
public class CreateRequestController {
    RegisterNewRequestUseCase registerNewRequestUseCase;

    private static ResponseEntity<Object> buildResponse(Result<String, Object> stringObjectResult) {
        return stringObjectResult.isSuccess()
               ? ResponseEntity.ok(stringObjectResult.getSuccess())
               : ResponseEntity.badRequest()
                               .body(stringObjectResult.getError());
    }

    @PostMapping ("")
    public ResponseEntity<?> handel(@RequestBody RequestCreation requestCreation) {
        return Optional.ofNullable(requestCreation)
                       .map(RequestCreation :: booksID)
                       .filter(booksID -> ! booksID.isEmpty())
                       .map(registerNewRequestUseCase :: createNewOder)
                       .map(CreateRequestController :: buildResponse)
                       .orElseGet(() -> ResponseEntity.badRequest()
                                                      .body("Bad Request given"));
    }

}
