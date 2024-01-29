package com.unir.webdev.books.infrastructure.controllers.DTO;

public record UpdateBookRequest(
        String name, String isbn, String image, String author, Integer releaseYear,
        String language, Double rate, Boolean available
) {

    public boolean validData() {
        return (available != null && name != null && isbn != null && image != null && author != null && releaseYear != null && language != null && rate != null);
    }
}
