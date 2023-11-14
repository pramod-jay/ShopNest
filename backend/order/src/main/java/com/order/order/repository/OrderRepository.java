package com.order.order.repository;

import com.order.order.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value ="SELECT * FROM orderdb.order a WHERE orderid = ?1", nativeQuery = true)
    Order fetchOrderById(Long id);

    @Query(value = "SELECT a.orderid  FROM orderdb.order a WHERE a.customer_id=?1", nativeQuery = true)
    List<Long> fetchOrderIdByCustomer(Long id);

//    @Query(value = "UPDATE orderdb.order a SET a.dp_id = ?1 WHERE (a.orderid = ?2)", nativeQuery = true)
//    Order assignDeliveryPerson(Long dpId, Long orderId);

    @Query(value = "SELECT a.dp_id FROM orderdb.order a WHERE orderid=?1", nativeQuery = true)
    Long checkAssignDeliverPerson(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orderdb.order a SET a.dp_id = ?2, a.status = 'Delivering' WHERE (a.orderid = ?1)", nativeQuery = true)
    void assignDeliveryPerson(Long orderId, Long dpId);

    @Query(value = "SELECT a.customer_id, a.orderid FROM orderdb.order a WHERE a.dp_id = ?1", nativeQuery = true)
    List<Long> fetchOrdersByDp(Long dpId);
 }
