package com.unir.webdev.orders.infrastructure.controllers;

import com.unir.webdev.orders.application.GetAllRequestsUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api/v1/requests")
@FieldDefaults (makeFinal = true, level = AccessLevel.PRIVATE)
public class GetAllRequestController {
    GetAllRequestsUseCase getAllRequestsUseCase;

    @GetMapping ("")
    public ResponseEntity<?> getAllRequest() {
        return ResponseEntity.ok(getAllRequestsUseCase.getAllRequest());
    }
}
