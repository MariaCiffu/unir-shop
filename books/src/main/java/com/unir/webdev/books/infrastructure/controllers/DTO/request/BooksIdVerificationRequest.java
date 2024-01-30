package com.unir.webdev.books.infrastructure.controllers.DTO.request;

import java.util.List;
import java.util.UUID;

public record BooksIdVerificationRequest(List<UUID> booksID) {
    public boolean isNotNullBooksID() {
        return booksID != null;
    }

}
