package com.unir.webdev.orders.infrastructure.controllers;

import com.unir.webdev.orders.application.DeleteRequestsUseCase;
import com.unir.webdev.orders.domain.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation (summary = "Delete Request", description = "Delete a request from the system by its ID")
    @ApiResponses (value = {
            @ApiResponse (responseCode = "200", description = "Request deleted successfully",
                          content = { @Content (mediaType = "application/json",
                                                schema = @Schema (implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request, the operation could not be completed",
                         content = @Content)
    })
    public ResponseEntity<?> deleteRequest(
            @Parameter (description = "UUID of the request to be deleted", required = true)
            @PathVariable UUID requestID
                                          ) {
        return Optional.ofNullable(requestID)
                       .map(deleteRequestsUseCase :: deleteRequest)
                       .map(DeleteRequestController :: buildResponse)
                       .orElseGet(() -> ResponseEntity.badRequest()
                                                      .body("Bad Resquest"));

    }
}
