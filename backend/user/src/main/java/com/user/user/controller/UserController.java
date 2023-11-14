package com.user.user.controller;

import com.order.order.util.VarList;
import com.user.user.dto.CredentialsDto;
import com.user.user.dto.CustomerMsgDto;
import com.user.user.dto.ResponseDto;
import com.user.user.dto.CustomerDto;
import com.user.user.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private ResponseDto responseDto;

    @PostMapping("/addCustomer")
    public ResponseEntity addUser(@RequestBody CustomerDto customerDto){
        String res = customerServiceImpl.addUser(customerDto);
        try{
            if(res.equals("00")){
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("Customer already exist in the db");
                responseDto.setContent(customerDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("01")){
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("Customer has been registered successfully");
                customerDto.setPassword("Password Encrypted");
                responseDto.setContent(customerDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("Function Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_DUPLICATED);
            responseDto.setMessage("Internal Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetchCustomerByID/{id}")
    public ResponseEntity fetchCustomerById(@PathVariable(name = "id")Long id){
        try{
            CustomerMsgDto res = customerServiceImpl.fetchCustomerById(id);
            if(res.getRES_Code().equals("02")){
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("User not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getRES_Code().equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("User fetched succefully");
                responseDto.setContent(res);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("Function Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_DUPLICATED);
            responseDto.setMessage("Internal Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/userLogin")
    public ResponseEntity userLogin(@RequestBody CredentialsDto credentialsDto){
        try{
            String res = customerServiceImpl.userLogin(credentialsDto);
            if(res.equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("User not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Login successful");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("08")){
                responseDto.setCode(VarList.RSP_PASSWORD_MISMATCHED);
                responseDto.setMessage("Password mismatched");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Internal Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
