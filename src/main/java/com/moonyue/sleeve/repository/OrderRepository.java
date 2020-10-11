package com.moonyue.sleeve.repository;

import com.moonyue.sleeve.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
