package com.delivery.delivery.service;

import com.delivery.delivery.dto.*;
import com.delivery.delivery.enitiy.DeliveryPerson;
import com.delivery.delivery.repository.DeliveryPersonRepository;
import com.delivery.delivery.util.Uri;
import com.delivery.delivery.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    private DPWithoutPWDto dpWithoutPWDto = new DPWithoutPWDto();

    final private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    final private Uri uri = new Uri();


    //Add delivery person
    @Override
    public String addDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
        if (deliveryPersonRepository.existsById(deliveryPersonDto.getId())) {
            return VarList.RSP_DUPLICATED;
        } else {
            deliveryPersonDto.setAvailability(true);
            System.out.println(deliveryPersonDto);
            deliveryPersonDto.setPassword(passwordEncoder.encode(deliveryPersonDto.getPassword()));
            deliveryPersonRepository.save(modelMapper.map(deliveryPersonDto, DeliveryPerson.class));
            return VarList.RSP_SUCCESS;
        }
    }

    //Fetch delivery person details(Without Password) by user name
    @Override
    public DPWithoutPWDto fetchDeliveryPerByUname(String uName) {
        DeliveryPerson dbRes = deliveryPersonRepository.fetchDeliveryPerByUname(uName);
        if (dbRes == null) {
            dpWithoutPWDto.setRSU_code(VarList.RSP_NOT_EXIST);
            return dpWithoutPWDto;
        } else {
            dpWithoutPWDto = modelMapper.map(dbRes, DPWithoutPWDto.class);
            dpWithoutPWDto.setRSU_code(VarList.RSP_SUCCESS);
            return dpWithoutPWDto;
        }
    }

    //Update delivery person's status
    @Override
    public String updateStatus(Boolean availability, Long id) {
        if (deliveryPersonRepository.existsById(id)) {
            deliveryPersonRepository.updateDeliveryPersonStatus(availability, id);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NOT_EXIST;
        }
    }

    //Fetch delivery person's availability
    @Override
    public String fetchAvailability(Long id) {
        Boolean res = deliveryPersonRepository.fetchAvailability(id);
        if (res) {
            return VarList.RSP_AVAILABLE;
        } else {
            return VarList.RSP_NOT_AVAILABLE;
        }
    }

    //Fetch available delivery persons
    @Override
    public AvailableDPersonDto fetchAvailablePersons() {
        final AvailableDPersonDto availableDPersonDto = new AvailableDPersonDto();
        List<String> dPersons = new ArrayList<>();
        List<Object[]> res = deliveryPersonRepository.fetchAvailablePersons();
        if (res == null) {
            availableDPersonDto.setRsu_code(VarList.RSP_NOT_EXIST);
            return availableDPersonDto;
        } else {
            for (Object[] row : res) {
                dPersons.add(Arrays.toString(row));
            }
            availableDPersonDto.setRsu_code(VarList.RSP_SUCCESS);
            availableDPersonDto.setDPersons(dPersons);
            return availableDPersonDto;
        }
    }

    //Assign orders to the delivery person
    @Override
    public String assignOrder(Long orderId, Long dpId) {
        Boolean isAvailable = deliveryPersonRepository.fetchAvailability(dpId);
        if (isAvailable) {
            ResponseDto res = restTemplate.getForObject(uri.orderUri() + "assignDeliveryPerson/" + orderId.toString() + "/" + dpId.toString(), ResponseDto.class);
            if (res.getCode().equals("05")) {
                return VarList.RSP_EXIST;
            } else if (res.getCode().equals("01")) {
                return VarList.RSP_SUCCESS;
            } else {
                return VarList.RSP_ERROR;
            }
        } else {
            return VarList.RSP_NOT_AVAILABLE;
        }
    }

    //Fetch orders by delivery person
    @Override
    public CustomerMsgDto fetchOrders(Long dpId) {
        CustomerMsgDto customerMsgDto = new CustomerMsgDto();
        ResponseDto res = restTemplate.getForObject(uri.orderUri() + "fetchOrdersByDp/" + dpId.toString(), ResponseDto.class);
        if(res.getCode().equals("02")){
                customerMsgDto.setRsu_code(VarList.RSP_NOT_EXIST);
                return customerMsgDto;
        }else{
            List<Integer> integerIds = (List<Integer>) res.getContent();
            List<Long> cusIds = integerIds.stream().map(Integer::longValue).collect(Collectors.toList());

            List<CustomerDto> customers = new ArrayList<>();
            for(Long id : cusIds){
                ResponseDto CusRes = restTemplate.getForObject(uri.userUri() + "fetchCustomerByID/" + id.toString(), ResponseDto.class);
                customers.add(modelMapper.map(CusRes.getContent(), CustomerDto.class));
            }
            customerMsgDto.setRsu_code(VarList.RSP_SUCCESS);
            customerMsgDto.setCustomerDtos(customers);
            return(customerMsgDto);
        }
    }

    //Delivery person login
    @Override
    public String deliveryLogin(CredentialsDto credentialsDto){
        DeliveryPerson res = deliveryPersonRepository.fetchDeliveryPerByUname(credentialsDto.getUserName());
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
