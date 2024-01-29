package com.unir.webdev.books.infrastructure.controllers.DTO;

public record CreateBookRequest(
        String name, String isbn, String image, String author, Integer releaseYear,
        String language, Double rate
) {
    public boolean validData() {
        return (name != null && isbn != null && image != null && author != null && releaseYear != null && language != null && rate != null);
    }
}
