package com.unir.webdev.books.application;

import com.unir.webdev.books.domain.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class IdVerificationUseCase {
    BookRepository bookRepository;

    public Boolean verify(List<UUID> booksID) {
        return booksID.stream()
                      .allMatch(bookRepository :: isValidBook);
    }
}
