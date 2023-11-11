package com.delivery.delivery.service;

import com.delivery.delivery.dto.DeliveryPersonDto;
import com.delivery.delivery.entity.DeliveryPerson;
import com.delivery.delivery.repository.DeliveryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public DeliveryPerson saveDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
        DeliveryPerson newDeliveryPerson = mapDtoToEntity(deliveryPersonDto);
        return deliveryPersonRepository.save(newDeliveryPerson);
    }
    @Override
    public List<DeliveryPerson> getAvailableDeliveryPersons() {
        // Implement the logic to retrieve a list of available delivery persons
        // You can use the DeliveryPersonRepository for this purpose

        return deliveryPersonRepository.findByAvailability("AVAILABLE");

    }

    @Override
    public Optional<DeliveryPerson> getDeliveryPersonById(Long dpId) {

        return deliveryPersonRepository.findById(dpId);
    }

    @Override
    public String getAvailabilityById(Long dp_id) {
        // Implement the logic to get the availability of a delivery person by ID
        Optional<DeliveryPerson> deliveryPersonOptional = getDeliveryPersonById(dp_id);

        if (deliveryPersonOptional.isPresent()) {
            return deliveryPersonOptional.get().getAvailability();
        } else {

            throw new RuntimeException("Delivery person not found with ID: " + dp_id);
        }
    }

    // Helper method to convert DeliveryPersonDto to DeliveryPerson entity
    private DeliveryPerson mapDtoToEntity(DeliveryPersonDto deliveryPersonDto) {
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setDp_name(deliveryPersonDto.getDp_name());
        deliveryPerson.setTelephone_num(deliveryPersonDto.getTelephoneNum());
        deliveryPerson.setAvailability(deliveryPersonDto.getAvailability());
        deliveryPerson.setVehicle_number(deliveryPersonDto.getVehicleNumber());
        return deliveryPerson;
    }


    @Override
    public void updateAvailability(Long dp_id, String newAvailability) {
        // Implementation for updating the availability of a delivery person by ID
        Optional<DeliveryPerson> deliveryPersonOptional = getDeliveryPersonById(dp_id);

        if (deliveryPersonOptional.isPresent()) {
            DeliveryPerson deliveryPerson = deliveryPersonOptional.get();
            deliveryPerson.setAvailability(newAvailability);
            deliveryPersonRepository.save(deliveryPerson);
        } else {
            throw new RuntimeException("Delivery person not found with ID: " + dp_id);
        }
    }

}
