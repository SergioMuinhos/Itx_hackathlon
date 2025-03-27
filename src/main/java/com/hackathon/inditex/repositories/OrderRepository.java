package com.hackathon.inditex.repositories;

import com.hackathon.inditex.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Order Repository.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
