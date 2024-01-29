package com.unir.webdev.books.infrastructure.persistence.entity.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Author(String author) {}
