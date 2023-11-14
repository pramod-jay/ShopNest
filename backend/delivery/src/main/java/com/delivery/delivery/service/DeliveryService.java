package com.delivery.delivery.service;

import com.delivery.delivery.dto.*;

public interface DeliveryService {
    String addDeliveryPerson(DeliveryPersonDto deliveryPersonDto);

    DPWithoutPWDto fetchDeliveryPerByUname(String uName);

    String updateStatus(Boolean Status, Long id);

    String fetchAvailability(Long id);

    AvailableDPersonDto fetchAvailablePersons();

    String assignOrder(Long orderId, Long dpId);

    CustomerMsgDto fetchOrders(Long dpId);

    String deliveryLogin(CredentialsDto credentialsDto);
}
