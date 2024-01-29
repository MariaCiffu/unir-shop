package com.unir.webdev.orders.application;

import com.unir.webdev.orders.domain.Request;
import com.unir.webdev.orders.domain.repository.RequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults (makeFinal = true, level = AccessLevel.PRIVATE)
public class GetAllRequestsUseCase {
    RequestRepository requestRepository;

    public List<Request> getAllRequest() {
        return requestRepository.getAllOrders();
    }
}
