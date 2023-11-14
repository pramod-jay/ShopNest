package com.delivery.delivery.cotroller;

import com.delivery.delivery.dto.*;
import com.delivery.delivery.service.DeliveryServiceImpl;
import com.delivery.delivery.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryServiceImpl deliveryService;

    @Autowired
    private ResponseDto responseDto;

    //Add delivery person
    @PostMapping("/addDeliveryPerson")
    public ResponseEntity addDeliveryPerson(@RequestBody DeliveryPersonDto deliveryPersonDto){
        try{
            String res = deliveryService.addDeliveryPerson(deliveryPersonDto);
            if(res.equals("00")){
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Delivery Person Already Exist");
                responseDto.setContent(deliveryPersonDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Delivery person has been registered successfully");
                deliveryPersonDto.setPassword("Password Encrypted");
                responseDto.setContent(deliveryPersonDto);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Internal Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Fetch delivery person details(Without password)
    @GetMapping("/fetchDeliveryPerByUname/{uName}")
    public ResponseEntity fetchDeliveryPerByUname(@PathVariable(name = "uName")String uName){
        try{
            DPWithoutPWDto res =deliveryService.fetchDeliveryPerByUname(uName);
            if(res.getRSU_code().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Delivery person not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getRSU_code().equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Delivery person details fetched successfully");
                responseDto.setContent(res);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Internal Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Update delivery person availability by Id
    @PutMapping("/updateDPersonStatus/{availability}/{id}")
    public ResponseEntity updateDPersonStatus(@PathVariable(name = "availability")Boolean availability, @PathVariable(name = "id")Long id){
        try{
            String res = deliveryService.updateStatus(availability, id);
            if(res.equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Delivery person not exist");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Delivery person availability updated successfully");
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
            responseDto.setMessage("Internal Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Fetch delivery person availability
    @GetMapping("/fetchAvailability/{id}")
    public ResponseEntity fetchAvailability(@PathVariable(name = "id")Long id){
        try{
            String res = deliveryService.fetchAvailability(id);
            if(res.equals("05")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Delivery person available at the moment");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.equals("06")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Delivery person not available at the moment");
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
            responseDto.setMessage("Internal Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the available delivery persons
    @GetMapping("/fetchAvailablePersons")
    public ResponseEntity fetchAvailablePersons(){
        try{
            AvailableDPersonDto res = deliveryService.fetchAvailablePersons();
            if(res.getRsu_code().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("All the delivery persons not available");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getRsu_code().equals("01")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Fetched available delivery persons");
                responseDto.setContent(res.getDPersons());
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Internal Server Error: "+e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Assign to orders
    @PutMapping("/assignOrders/{orderId}/{dPId}")
    public ResponseEntity assignOrders(@PathVariable(name = "orderId")Long orderId, @PathVariable(name = "dPId")Long dpId) {
        try {
            String res = deliveryService.assignOrder(orderId, dpId);
            if (res.equals("06")) {
                responseDto.setCode(VarList.RSP_NOT_AVAILABLE);
                responseDto.setMessage("Delivery person not available at the moment");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            } else if (res.equals("07")) {
                responseDto.setCode(VarList.RSP_EXIST);
                responseDto.setMessage("Delivery person has assigned to this order already");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            } else if (res.equals("01")) {
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Delivery person has been assigned to the order successfully");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else{
                responseDto.setCode(VarList.RSP_ERROR);
                responseDto.setMessage("Function Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage("Internal Server Error: " + e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("fetchOrders/{dpId}")
    public ResponseEntity fetchOrders(@PathVariable(name = "dpId")Long dpId){
        try{
            CustomerMsgDto res = deliveryService.fetchOrders(dpId);
            if(res.getRsu_code().equals("02")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("This delivery person has not any order");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }else if(res.getRsu_code().equals("01")){
                responseDto.setCode(VarList.RSP_NOT_EXIST);
                responseDto.setMessage("Orders' data fetched successfully");
                responseDto.setContent(res.getCustomerDtos());
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

    @PostMapping("/deliveryLogin")
    public ResponseEntity deliveryLogin(@RequestBody CredentialsDto credentialsDto){
        try{
            String res = deliveryService.deliveryLogin(credentialsDto);
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