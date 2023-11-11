package com.order.order.repository;

import com.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value ="SELECT * FROM orderdb.order a WHERE orderid = ?1", nativeQuery = true)
    Order fetchOrderById(Long id);
 }
