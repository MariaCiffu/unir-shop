package com.unir.webdev.books.infrastructure.controllers.DTO.request;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.UUID;

public record ChangeAvailabilityRequest(List<UUID> booksID) {
    @Contract (pure = true)
    public boolean existBooks() {
        return booksID != null;
    }
}
