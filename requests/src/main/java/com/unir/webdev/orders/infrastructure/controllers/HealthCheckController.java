package com.unir.webdev.orders.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/request")
public class HealthCheckController {
    @GetMapping("check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("ok");
    }
}
