package com.mycart.mycart.Service;

import com.mycart.mycart.Dto.OrderDto;
import com.mycart.mycart.Entities.Cart;
import com.mycart.mycart.Entities.Order;
import com.mycart.mycart.Entities.OrderIteam;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Order placeorder(long userid);
    OrderDto getorder(long orderid);
    Set<OrderIteam> createOrerIteam(Order order, Cart cart);
    Order createOder(Cart cart);
    List<OrderDto> getOrderByUser(long userid);
    OrderDto concertToDto(Order order);
}
