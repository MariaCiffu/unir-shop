package com.unir.webdev.orders.infrastructure.events;

import com.unir.webdev.orders.domain.events.ResquestEvents;
import com.unir.webdev.orders.infrastructure.controllers.dto.RequestChangeBookAvailability;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@FieldDefaults (level = AccessLevel.PRIVATE, makeFinal = true)
public class ChangeAvailabilityEvent implements ResquestEvents {
    RestTemplate restTemplate;

    public void sendEventChangeAvailability(List<UUID> booksList) {
        String url = "http://books:8082/api/v1/books/availability";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var requestEntity =
                new HttpEntity<>(new RequestChangeBookAvailability(booksList), headers);
        var s = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, String.class);
        System.out.println(s.getBody());
    }
}
