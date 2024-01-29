package com.unir.webdev.orders.domain.repository;

import com.unir.webdev.orders.domain.Request;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository {
    void registerNewOrder(Request request);

    List<Request> getAllOrders();

    void deleteOrder(UUID uuid);

    Optional<Request> getRequestById(UUID id);

    boolean unknownRequest(UUID id);

    List<UUID> getBookIDsOfRequest(UUID id);

    boolean isEmptyRequest(UUID id);
}
