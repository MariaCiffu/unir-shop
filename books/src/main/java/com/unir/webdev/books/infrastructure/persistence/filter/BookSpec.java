package com.unir.webdev.books.infrastructure.persistence.filter;

import com.unir.webdev.books.infrastructure.persistence.entity.BookEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class BookSpec {
    public Specification<BookEntity> filterColumns(
            String name, String author
                                                  ) {
        return (root, query, criteriaBuilder) -> {
            var predicateNameStream = Stream.of();
            var predicateAuthorStream = Stream.of();
            if (name != null) {
                predicateNameStream = Stream.of(criteriaBuilder.like(root.get("bookName")
                                                                         .get("bookName"
                                                                             ),
                                                                     "%" + name + "%"));
            }
            if (author != null) {
                predicateAuthorStream = Stream.of(criteriaBuilder.like(root.get("author")
                                                                           .get("author"
                                                                               ),
                                                                       "%" + author +
                                                                       "%"));
            }

            return criteriaBuilder.and(Stream.concat(predicateAuthorStream,
                                                     predicateNameStream)
                                             .toArray(Predicate[] :: new));
        };
    }

}
