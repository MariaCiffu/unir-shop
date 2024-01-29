package com.unir.webdev.books.domain.events;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface BookEvents {
    boolean requestBooksCreation(List<UUID> booksID);
}
