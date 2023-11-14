package com.user.user.service;

import com.order.order.util.VarList;
import com.user.user.dto.CredentialsDto;
import com.user.user.dto.CustomerDto;
import com.user.user.dto.CustomerMsgDto;
import com.user.user.entity.Customer;
import com.user.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    final private CustomerMsgDto customerMsgDto = new CustomerMsgDto();

    final private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Add customer
    @Override
    public String addUser(CustomerDto customerDto){
        if(customerExist(customerDto.getUserName())){
            return VarList.RSP_DUPLICATED;
        }else{
            customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
            userRepository.save(modelMapper.map(customerDto, Customer.class));
            return VarList.RSP_SUCCESS;
        }
    }

    //Fetch customer by username
    @Override
    public CustomerMsgDto fetchCustomerById(Long id){
        Customer res = userRepository.fetchCustomerByID(id);
        if(res == null){
            customerMsgDto.setRES_Code(VarList.RSP_NOT_EXIST);
            return customerMsgDto;
        }else{
            CustomerMsgDto customerMsgDto1 = modelMapper.map(res, CustomerMsgDto.class);
            customerMsgDto1.setRES_Code(VarList.RSP_SUCCESS);
            return customerMsgDto1;
        }
    }

    //Check customer exist or not by UserName
    @Override
    public Boolean customerExist(String userName){
        Customer res = userRepository.customerExist(userName);
        if(res!=null){
            return true;
        }else{
            return false;
        }
    }


    //User login
    @Override
    public String userLogin(CredentialsDto credentialsDto){
        Customer res = userRepository.customerExist(credentialsDto.getUserName());
        if(res==null){
            return VarList.RSP_NOT_EXIST;
        }else{
            if(passwordEncoder.matches(credentialsDto.getPassword(), res.getPassword())){
                return VarList.RSP_SUCCESS;
            }
            return VarList.RSP_PASSWORD_MISMATCHED;
        }
    }
}
