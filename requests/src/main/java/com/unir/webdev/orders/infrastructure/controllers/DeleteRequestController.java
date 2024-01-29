package com.unir.webdev.orders.infrastructure.controllers;

import com.unir.webdev.orders.application.DeleteRequestsUseCase;
import com.unir.webdev.orders.domain.response.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping ("/api/v1/requests")
@RequiredArgsConstructor
@FieldDefaults (makeFinal = true, level = AccessLevel.PRIVATE)
public class DeleteRequestController {
    DeleteRequestsUseCase deleteRequestsUseCase;

    private static ResponseEntity<String> buildResponse(Result<String, Object> stringStringResult) {
        return stringStringResult.isSuccess()
               ? ResponseEntity.ok(stringStringResult.getSuccess())
               : ResponseEntity.badRequest()
                               .body(stringStringResult.getError()
                                                       .toString());
    }

    @DeleteMapping("/{requestID}")
    public ResponseEntity<?> deleteRequest(@PathVariable UUID requestID) {
        return Optional.ofNullable(requestID)
                       .map(deleteRequestsUseCase :: deleteRequest)
                       .map(DeleteRequestController :: buildResponse)
                       .orElseGet(() -> ResponseEntity.badRequest()
                                                      .body("Bad Resquest"));

    }
}
