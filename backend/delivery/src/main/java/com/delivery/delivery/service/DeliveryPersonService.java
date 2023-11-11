package com.delivery.delivery.service;

import com.delivery.delivery.dto.DeliveryPersonDto;
import com.delivery.delivery.entity.DeliveryPerson;
import com.delivery.delivery.repository.DeliveryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DeliveryPersonService {
    DeliveryPerson saveDeliveryPerson(DeliveryPersonDto deliveryPersonDto);
    List<DeliveryPerson> getAvailableDeliveryPersons();

    Optional<DeliveryPerson> getDeliveryPersonById(Long dp_id);
    String getAvailabilityById(Long dp_id);


    void updateAvailability(Long dp_id, String newAvailability);
}
