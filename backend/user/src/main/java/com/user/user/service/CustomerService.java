package com.user.user.service;

import com.user.user.dto.CredentialsDto;
import com.user.user.dto.CustomerDto;
import com.user.user.dto.CustomerMsgDto;

public interface CustomerService {
    String addUser(CustomerDto customerDto);
    Boolean customerExist(String userName);
    CustomerMsgDto fetchCustomerById(Long id);
    String userLogin(CredentialsDto credentialsDto);
}
