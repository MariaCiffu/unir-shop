package com.unir.webdev.orders.infrastructure.persistence;

import com.unir.webdev.orders.infrastructure.persistence.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestRepositoryJPA extends JpaRepository<Request, UUID> {}
