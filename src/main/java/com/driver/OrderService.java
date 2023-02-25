package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order)
    {
        orderRepository.addOrder(order);
    }

    public void addPatner(String id)
    {
        orderRepository.addPatner(id);
    }

    public void addOrderPartnerPair(String orderId,String partnerId)
    {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }
}
