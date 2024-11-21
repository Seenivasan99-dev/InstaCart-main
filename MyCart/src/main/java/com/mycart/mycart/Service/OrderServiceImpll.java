package com.mycart.mycart.Service;

import com.mycart.mycart.Dto.CartIteamDto;
import com.mycart.mycart.Dto.OrderDto;
import com.mycart.mycart.Dto.OrderIteamDto;
import com.mycart.mycart.Entities.*;
import com.mycart.mycart.Exceptions.OrderNotFound;
import com.mycart.mycart.Repository.OrderRepo;
import com.mycart.mycart.Repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpll implements OrderService{
    @Autowired
    OrderRepo orderrepo;

    @Autowired
    ProductRepo productrepo;

    @Autowired
    CartService cartservice;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Order placeorder(long userid) {
        Cart cart=cartservice.getcarbyUserId(userid);
        Order order=createOder(cart);
        Set<OrderIteam> orderiteams=  createOrerIteam(order,cart);
        order.setOrderiteam(orderiteams);
        order.setTotalorderamount(CalaculateTotalAmount(orderiteams));
        Order savedorde=orderrepo.save(order);
        cartservice.clearCart(cart.getId());
        return savedorde;
    }



    @Override
    public OrderDto getorder(long orderid) {

        return  orderrepo.findById(orderid).map(this::concertToDto).orElseThrow(()->{
            throw new OrderNotFound("order not found");
        });
    }



    @Override
    public Set<OrderIteam> createOrerIteam(Order order, Cart cart) {
        return cart.getCartiteam().stream().map(iteam->{
            Product p=iteam.getProduct();
            p.setInventory(p.getInventory()-iteam.getQuantity());
            productrepo.save(p);
            return new OrderIteam(order,p,iteam.getQuantity(),iteam.getTotalPrice());
        }).collect(Collectors.toSet());
    }

    @Override
    public Order createOder(Cart cart) {
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderstatus(OrderStatus.PENDING);
        order.setOrderdate(LocalDate.now());
        return order;
    }

    @Override
    public List<OrderDto> getOrderByUser(long userid) {
        List<Order> orders= orderrepo.findByUserId(userid);
        return orders.stream().map(this::concertToDto).toList();
    }

    public double CalaculateTotalAmount(Set<OrderIteam> orderiteam) {
        return orderiteam.stream().mapToDouble(iteam->iteam.getPrice()* iteam.getQuantity()).sum();
    }

    @Override
    public OrderDto concertToDto(Order order){
//        modelMapper.typeMap(Order.class, OrderDto.class).addMappings(mp->{
//            mp.map(Order::getOrderiteam,OrderDto::setOrderiteam);
//            mp.map(Order::getOrderdate,OrderDto::setOrderdate);
//            mp.map(Order::getOrderstatus,OrderDto::setStatus);
//            mp.map(Order::getTotalorderamount,OrderDto::setTotalorderamount);
//        });
        ModelMapper modelMapper2 = new ModelMapper();
        modelMapper2.addMappings(new PropertyMap<Order,OrderDto>() {
            @Override
            protected void configure() {
                map(source.getUser().getId(),destination.getUserid());
            }
        });

        modelMapper2.addMappings(new PropertyMap<OrderIteam,OrderIteamDto>() {

            @Override
            protected void configure() {
                map(source.getProduct().getId(),destination.getProductid());
            }
        });

        modelMapper2.addMappings(new PropertyMap<Order,OrderDto>() {

            @Override
            protected void configure() {
                map(source.getOrderdate(),destination.getOrderdate());
            }
        });
        return modelMapper2.map(order,OrderDto.class);
    }



}
