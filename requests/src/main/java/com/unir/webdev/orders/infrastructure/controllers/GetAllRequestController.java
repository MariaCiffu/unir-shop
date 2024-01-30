package com.unir.webdev.orders.infrastructure.controllers;

import com.unir.webdev.orders.application.GetAllRequestsUseCase;
import com.unir.webdev.orders.domain.Request;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("")
    @Operation (summary = "Get All Requests", description = "Retrieve all the requests from the system")
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Requests retrieved successfully",
                          content = { @Content (mediaType = "application/json",
                                                schema = @Schema (implementation = Request.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, something went wrong",
                         content = @Content)
    })
    public ResponseEntity<?> getAllRequest() {
        return ResponseEntity.ok(getAllRequestsUseCase.getAllRequest());
    }
}
