package com.delivery.delivery.controller;


import com.delivery.delivery.dto.DeliveryPersonDto;
import com.delivery.delivery.dto.ResponseDto;
import com.delivery.delivery.entity.Delivery;
import com.delivery.delivery.entity.DeliveryPerson;
import com.delivery.delivery.service.DeliveryPersonService;
import com.delivery.delivery.service.DeliveryService;
import com.delivery.delivery.service.DeliveryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {


    @Autowired
    private DeliveryServiceImpl deliveryServiceImpl;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/addDeliveryPerson")
    public ResponseEntity<DeliveryPerson> addDeliveryPerson(@RequestBody DeliveryPersonDto deliveryPersonDto) {
    DeliveryPerson newDeliveryPerson = deliveryPersonService.saveDeliveryPerson(deliveryPersonDto);
    return new ResponseEntity<>(newDeliveryPerson, HttpStatus.CREATED);
    }


        @GetMapping("/getAvailability/{dp_id}")
        public ResponseEntity<String> getAvailability(@PathVariable Long dp_id) {
            String availability = deliveryPersonService.getAvailabilityById(dp_id);
            return new ResponseEntity<>(availability, HttpStatus.OK);
        }
    @PostMapping("/updateAvailability/{dp_id}/{newAvailability}")
    public ResponseEntity<String> updateAvailability(@PathVariable Long dp_id, @PathVariable String newAvailability) {
        deliveryPersonService.updateAvailability(dp_id, newAvailability);
        return new ResponseEntity<>("Availability updated successfully", HttpStatus.OK);
    }


    @PostMapping("/assignDelivery/{dp_id}/{orderID}")
    public ResponseEntity<Delivery> assignDelivery(@PathVariable Long dp_id, @PathVariable Long orderID) {
        Delivery assignedDelivery = deliveryService.assignDelivery(dp_id, orderID);
        return new ResponseEntity<>(assignedDelivery, HttpStatus.CREATED);
    }
    }




