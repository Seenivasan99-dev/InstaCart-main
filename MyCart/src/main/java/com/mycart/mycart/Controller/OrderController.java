package com.mycart.mycart.Controller;

import com.mycart.mycart.Dto.OrderDto;
import com.mycart.mycart.Entities.Order;
import com.mycart.mycart.Response.OrderApiResponse;
import com.mycart.mycart.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderApiResponse> createorder(@RequestParam long userid){
        try {
            Order order = orderService.placeorder(userid);
            OrderDto orderdto=orderService.concertToDto(order);
            return ResponseEntity.ok(new OrderApiResponse("orderSucess", orderdto));
        }
        catch(Exception e){
            return ResponseEntity.ok(new OrderApiResponse("error", e));
        }
    }
    @GetMapping("/getorders/userid")
    public ResponseEntity<OrderApiResponse> getorder(@RequestParam long userid){
        try {
            List<OrderDto> orders = orderService.getOrderByUser(userid);
            return ResponseEntity.ok(new OrderApiResponse("orderSucess", orders));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new OrderApiResponse("error", e));
        }
    }

    public ResponseEntity<OrderApiResponse> getorderbyid(@RequestParam long orderid){
        try {
            OrderDto orderDto=orderService.getorder(orderid);
            return ResponseEntity.ok(new OrderApiResponse("orderSucess", orderDto));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new OrderApiResponse("error", e));
        }
    }



}
