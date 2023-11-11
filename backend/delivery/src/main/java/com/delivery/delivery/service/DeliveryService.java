package com.delivery.delivery.service;

import com.delivery.delivery.entity.Delivery;
import org.springframework.stereotype.Service;

@Service
public interface DeliveryService {

    Delivery assignDelivery(Long dp_id, Long orderID);
}
