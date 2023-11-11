package com.delivery.delivery.service;

import com.delivery.delivery.entity.Delivery;
import com.delivery.delivery.entity.DeliveryPerson;
import com.delivery.delivery.repository.DeliveryPersonRepository;
import com.delivery.delivery.repository.DeliveryRepository;
import com.order.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public Delivery assignDelivery(Long dp_id, Long orderID) {
        // Check if the delivery person is available
        Optional<DeliveryPerson> deliveryPersonOptional = deliveryPersonRepository.findById(dp_id);

        if (deliveryPersonOptional.isPresent()) {
            DeliveryPerson deliveryPerson = deliveryPersonOptional.get();

            if ("AVAILABLE".equals(deliveryPerson.getAvailability())) {
                // If the delivery person is available, create and save a new delivery
                Delivery newDelivery = new Delivery();
                newDelivery.setDp_id(dp_id);
                newDelivery.setOrder_id(orderID);
                return deliveryRepository.save(newDelivery);
            } else {

                throw new RuntimeException("Delivery person with ID " + dp_id + " is not available at the moment");
            }
        } else {

            throw new RuntimeException("Delivery person not found with ID: " + dp_id);
        }
    }
}
