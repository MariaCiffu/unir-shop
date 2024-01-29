package com.unir.webdev.orders.domain;

import com.unir.webdev.orders.domain.valueObjects.DateInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class Request {
    UUID id;
    DateInfo dateInfo;
    List<UUID> booksID;

    public static Request of(List<UUID> booksID) {
        return new Request(UUID.randomUUID(), DateInfo.of(), booksID);
    }
}
