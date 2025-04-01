package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity createOrder(OrderEntity order);
    OrderEntity updateOrder(Long orderId, OrderEntity orderDetails);
    void deleteOrder(Long orderId);
    OrderEntity getOrderById(Long orderId);
    List<OrderEntity> getAllOrders();
}
