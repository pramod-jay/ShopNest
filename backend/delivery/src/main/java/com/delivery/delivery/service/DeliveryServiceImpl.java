package com.delivery.delivery.service;

import com.delivery.delivery.entity.Delivery;
import com.delivery.delivery.entity.DeliveryPerson;
import com.delivery.delivery.repository.DeliveryPersonRepository;
import com.delivery.delivery.repository.DeliveryRepository;
import com.order.order.dto.ItemMsgDto;
import com.order.order.dto.OrderDto;
import com.order.order.dto.ResponseDto;
import com.order.order.entity.OrderItem;
import com.order.order.service.OrderService;
import com.order.order.util.Uri;
import com.order.order.util.VarList;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Delivery assignDelivery(Long dp_id, Long orderID) {
        // Check if the delivery person is available
        Optional<DeliveryPerson> deliveryPersonOptional = deliveryPersonRepository.findById(dp_id);

        if (deliveryPersonOptional.isPresent()) {
            DeliveryPerson deliveryPerson = deliveryPersonOptional.get();

            if ("AVAILABLE".equals(deliveryPerson.getAvailability())) {
                // Check if the order with the given ID exists and is suitable for delivery
                OrderDto orderDto = orderService.getOrderById(orderID);

                if (orderDto != null) {
                    // If the order exists, create and save a new delivery
                    Delivery newDelivery = new Delivery();
                    newDelivery.setDp_id(dp_id);
                    newDelivery.setOrder_id(orderID);
                    return deliveryRepository.save(newDelivery);
                } else {
                    throw new RuntimeException("There is no order available from order ID: " + orderID);
                }
            } else {
                throw new RuntimeException("Delivery person with ID " + dp_id + " is not available at the moment");
            }
        } else {
            throw new RuntimeException("Delivery person not found with ID: " + dp_id);
        }
    }
}
