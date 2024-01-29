package com.unir.webdev.books.infrastructure.events;

import com.unir.webdev.books.domain.events.BookEvents;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class BookEventimplementation implements BookEvents {

    RestTemplate restTemplate;

    @Override
    public boolean requestBooksCreation(List<UUID> booksID) {
        String url = "http://requests:8081/api/v1/requests";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var requestEntity = new HttpEntity<>(new RequestCreation(booksID), headers);
        restTemplate.postForObject(url, requestEntity, String.class);
        return true;
    }
}
