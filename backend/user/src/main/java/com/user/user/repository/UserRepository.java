package com.user.user.repository;

import com.user.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM userdb.customer a WHERE a.user_name = ?1", nativeQuery = true)
    Customer customerExist(String userName);

    @Query(value = "SELECT * FROM userdb.customer a WHERE a.cus_id = ?1", nativeQuery = true)
    Customer fetchCustomerByID(Long id);
}
