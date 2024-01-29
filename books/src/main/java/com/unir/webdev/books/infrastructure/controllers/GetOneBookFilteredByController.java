package com.unir.webdev.books.infrastructure.controllers;

import com.unir.webdev.books.application.GetAllBooksUseCase;
import com.unir.webdev.books.application.GetBookByUseCase;
import com.unir.webdev.books.infrastructure.controllers.DTO.request.GetBookByRequest;
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
    public ResponseEntity<?> getBooks(GetBookByRequest request) {
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
