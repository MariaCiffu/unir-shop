package com.unir.webdev.orders.application;

import com.unir.webdev.orders.domain.repository.RequestRepository;
import com.unir.webdev.orders.domain.response.Result;
import com.unir.webdev.orders.infrastructure.events.ChangeAvailabilityEvent;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults (makeFinal = true, level = AccessLevel.PRIVATE)
public class DeleteRequestsUseCase {
    RequestRepository requestRepository;
    ChangeAvailabilityEvent changeAvailabilityEvent;

    public Result<String, Object> deleteRequest(UUID requestID) {
        return Optional.ofNullable(requestID)
                       .filter(uuid -> ! requestRepository.unknownRequest(uuid))
                       .filter(uuid -> ! requestRepository.isEmptyRequest(requestID))
                       .map(this :: deleteRequestProcess)
                       .map(this :: sendEventChangeAvailability)
                       .orElse(Result.error("Not Valid data"));

    }

    private @NotNull Result<String, Object> sendEventChangeAvailability(List<UUID> uuids) {
        changeAvailabilityEvent.sendEventChangeAvailability(uuids);
        return Result.success("Deleted Request");
    }

    private List<UUID> deleteRequestProcess(UUID id) {
        List<UUID> bookIDsOfRequest = requestRepository.getBookIDsOfRequest(id);
        requestRepository.deleteOrder(id);
        return bookIDsOfRequest;
    }
}
