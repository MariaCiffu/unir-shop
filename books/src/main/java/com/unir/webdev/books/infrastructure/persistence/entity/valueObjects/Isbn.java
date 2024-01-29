package com.unir.webdev.books.infrastructure.persistence.entity.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Isbn(String isbn) {}
