package com.order.order.service;

import com.order.order.dto.*;
import com.order.order.entity.Order;
import com.order.order.entity.OrderItem;
import com.order.order.repository.OrderRepository;
import com.order.order.util.Uri;
import com.order.order.util.VarList;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.aspectj.weaver.ast.Var;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    final ItemMsgDto itemMsgDto = new ItemMsgDto();
    final OrderMsgDto orderMsgDto = new OrderMsgDto();

    final Uri uri = new Uri();

    double total=0.0;

    //Add order
    @Override
    public ItemMsgDto addOrder(@NotNull OrderDto orderDto) {

        orderDto.setDate(LocalDate.now());
        orderDto.setTime(LocalTime.now());
        orderDto.setStatus("Pending");

        if (orderRepository.existsById(orderDto.getOrderID())) {  //Check order is already available;
            itemMsgDto.setRSU_code(VarList.RSP_DUPLICATED);
            return itemMsgDto;
        } else {
            List<OrderItem> orderItemList = orderDto.getOrderItemList(); //Check ordered item availability in the inventory
            for (OrderItem orderItem : orderItemList) {
                ResponseDto res = restTemplate.getForObject((uri.inventoryUri()+"fetchItem/"+ orderItem.getItemId().toString()), ResponseDto.class); //Get item data by calling inventory MS
                if (res.getCode().equals("02")) {
                    itemMsgDto.setId(orderItem.getItemId());
                    itemMsgDto.setRSU_code(VarList.RSP_NOT_EXIST);
                    return itemMsgDto;
                }

                //Check ordered quantity and available quantity
                ItemDto resOrderItem = modelMapper.map(res.getContent(), ItemDto.class);
                if (resOrderItem.getQuantity() < orderItem.getQuantity()) {
                    itemMsgDto.setId(orderItem.getItemId());
                    itemMsgDto.setRSU_code(VarList.RSP_NOT_ENOUGH);
                    return itemMsgDto;
                }

                //Update quantity of ordered item in inventory MS
                resOrderItem.setQuantity(resOrderItem.getQuantity()-orderItem.getQuantity());
                System.out.println(resOrderItem);
                restTemplate.put(uri.inventoryUri()+"updateItem", resOrderItem);

                //Calculate total of each item and total amount of order
                double totalItemPrice = resOrderItem.getPrice() * orderItem.getQuantity();
                orderItem.setItemPrice(totalItemPrice);
                total+=totalItemPrice;
            }

            orderDto.setTotal(total); //Set calculated bill total to the DTO
            orderRepository.save(modelMapper.map(orderDto, Order.class)); //Call API for save the order
            itemMsgDto.setRSU_code(VarList.RSP_SUCCESS);
            return itemMsgDto;
        }
    }

    //Fetch order by order id
    @Override
    public OrderMsgDto fetchOrderById(Long orderId){
        Order res = orderRepository.fetchOrderById(orderId);
        if(res == null){ //If order not available
            orderMsgDto.setRSU_CODE(VarList.RSP_NOT_EXIST);
            return orderMsgDto;
        }else{
            //Set data to the response
            orderMsgDto.setRSU_CODE(VarList.RSP_SUCCESS);
            orderMsgDto.setOrderID(res.getOrderID());
            orderMsgDto.setDate(res.getDate());
            orderMsgDto.setTime(res.getTime());
            orderMsgDto.setStatus(res.getStatus());
            orderMsgDto.setTotal(res.getTotal());
            orderMsgDto.setCustomerId(res.getCustomerId());
            orderMsgDto.setDp_id(res.getDp_id());

            //Get details of each item in the order
            List<OrderMsgListDto> orderMsgListDtoList = new ArrayList<>();
            List<OrderItem> orderItemList = res.getOrderItemList();
            for(OrderItem orderItem : orderItemList){
                final OrderMsgListDto orderMsgListDto = new OrderMsgListDto();

                ResponseDto itemRes = restTemplate.getForObject((uri.inventoryUri()+"fetchItemNamePrice/"+ orderItem.getItemId().toString()), ResponseDto.class); //Fetch item name and price by itemID
                ItemDto item = modelMapper.map(itemRes.getContent(), ItemDto.class);

                orderMsgListDto.setItemId(orderItem.getOrderItemId());
                orderMsgListDto.setItemName(item.getItem_name());
                orderMsgListDto.setUnitPrice(item.getPrice());
                orderMsgListDto.setQuantity(orderItem.getQuantity());
                orderMsgListDto.setTotalPrice(orderItem.getItemPrice());


                orderMsgListDtoList.add(orderMsgListDto);
            }
            orderMsgDto.setOrderMsgListDtoList(orderMsgListDtoList);
            return orderMsgDto;
        }
    }

    //Fetch order by customer
    @Override
    public CusOrderMsgDto fetchOrderByCustomer(Long cusId){
        ResponseDto res = restTemplate.getForObject((uri.userUri()+"fetchCustomerByID/"+ cusId), ResponseDto.class);
        CusOrderMsgDto cusOrderMsgDto = new CusOrderMsgDto();
        if(res.getCode().equals("02")){
            cusOrderMsgDto.setRSU_Code(VarList.RSP_NOT_EXIST);
            return cusOrderMsgDto;
        }else if(res.getCode().equals("01")){
            cusOrderMsgDto = modelMapper.map(res.getContent(), CusOrderMsgDto.class);
            cusOrderMsgDto.setRSU_Code(VarList.RSP_SUCCESS);
            List<Long> orderIdList = orderRepository.fetchOrderIdByCustomer(cusId);
            List<OrderMsgDto> ordersList = new ArrayList<>();
            for(Long orderId : orderIdList){
                OrderMsgDto order = fetchOrderById(orderId);
                ordersList.add(order);
            }
            cusOrderMsgDto.setOrderMsgDtos(ordersList);
            return cusOrderMsgDto;
        }else{
            cusOrderMsgDto.setRSU_Code(VarList.RSP_ERROR);
            return  cusOrderMsgDto;
        }
    }

    //Assign delivery person
    @Override
    public String assignDeliveryPerson(Long orderId, Long dpId) {
        Long isAssigned = orderRepository.checkAssignDeliverPerson(orderId);
        if(isAssigned!=null){
            return VarList.RSP_EXIST;
        }else{
            orderRepository.assignDeliveryPerson(orderId, dpId);
            return VarList.RSP_SUCCESS;
        }
    }

    //Fetch orders by delivery person
    @Override
    public List<Long> fetchOrdersByDp(Long dpId){
        return orderRepository.fetchOrdersByDp(dpId);
    }
}
