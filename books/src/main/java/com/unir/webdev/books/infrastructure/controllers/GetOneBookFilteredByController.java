package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.GetAllBooksUseCase;
import com.unir.webdev.books.application.GetBookByUseCase;
import com.unir.webdev.books.domain.Book;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.GetBookByRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Value
@RestController
@RequiredArgsConstructor
@RequestMapping ("api/v1/books")
@FieldDefaults (makeFinal = true, level = PRIVATE)
public class GetOneBookFilteredByController {
    GetBookByUseCase getBookByUseCase;
    GetAllBooksUseCase getAllBooksUseCase;

    @GetMapping ("")
    @Operation (summary = "Get Book by Filter", description = "Get a book by name or " +
                                                              "author, or get all books" +
                                                              " if no filter is provided")
    @ApiResponses (value = {@ApiResponse (responseCode = "200", description = "Book(s) " +
                                                                              "retrieved successfully", content = {@Content (mediaType = "application/json", schema = @Schema (implementation = Book.class))}), @ApiResponse (responseCode = "404", description = "No book found with the provided filters", content = @Content)})
    public ResponseEntity<?> getBooks(
            @Parameter (description = "GetBookByRequest object to filter books by name " +
                                      "or author") GetBookByRequest request
                                     ) {
        return Optional.ofNullable(request)
                       .filter(getBookByRequest1 -> GetBookByRequest.existAuthor(getBookByRequest1) || GetBookByRequest.existBookName(getBookByRequest1))
                       .map(getBookByRequest -> getBookByUseCase.getBookBy(request.name(), request.author()))
                       .map(books -> ResponseEntity.ok()
                                                   .body(books))
                       .orElse(ResponseEntity.ok()
                                             .body(getAllBooksUseCase.getAllProducts()
                                                                     .getSuccess()));
    }

}
