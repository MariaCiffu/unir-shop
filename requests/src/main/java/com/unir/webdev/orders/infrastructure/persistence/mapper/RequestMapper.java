package com.unir.webdev.orders.infrastructure.persistence.mapper;

import com.unir.webdev.orders.infrastructure.persistence.entity.Request;
import com.unir.webdev.orders.infrastructure.persistence.entity.valueObjects.DateInfo;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    public Request adaptDomainToDb(com.unir.webdev.orders.domain.Request request) {
        return new Request(request.getId(), new DateInfo(request.getDateInfo()
                                                                .creationDate()),
                           request.getBooksID());
    }

    public com.unir.webdev.orders.domain.Request adaptDbToDomain(Request request) {
        return new com.unir.webdev.orders.domain.Request(request.getId(),
                                                         new com.unir.webdev.orders.domain.valueObjects.DateInfo(request.getDateInfo()
                                                                                                                                         .creationDate()), request.getBooksID());
    }
}
