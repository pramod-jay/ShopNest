package com.delivery.delivery.repository;

import com.delivery.delivery.dto.AvailableDPersonDto;
import com.delivery.delivery.dto.DPWithoutPWDto;
import com.delivery.delivery.enitiy.DeliveryPerson;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson,Long> {
    @Query(value = "SELECT * FROM deliverydb.delivery_person a WHERE a.user_name=?1", nativeQuery = true)
    DeliveryPerson fetchDeliveryPerByUname(String uName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE deliverydb.delivery_person a SET a.availability = ?1 WHERE (a.id = ?2)", nativeQuery = true)
    void updateDeliveryPersonStatus(Boolean availability, Long id);

    @Query(value = "SELECT a.availability FROM deliverydb.delivery_person a WHERE a.id = ?1", nativeQuery = true)
    Boolean fetchAvailability(Long id);

    @Query(value = "SELECT a.id, a.name, a.tp_no, a.user_name FROM deliverydb.delivery_person a WHERE a.availability = 1", nativeQuery = true)
    List<Object[]> fetchAvailablePersons();
}
