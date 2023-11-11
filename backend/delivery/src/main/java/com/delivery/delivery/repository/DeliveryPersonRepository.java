package com.delivery.delivery.repository;

import com.delivery.delivery.entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {

    List<DeliveryPerson> findByAvailability(String available);
}
