package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity createOrder(OrderEntity orderEntity);
    OrderEntity updateOrder(Long orderId, OrderEntity orderDetailsEntity);
    void deleteOrder(Long orderId);
    OrderEntity getOrderById(Long orderId);
    List<OrderEntity> getAllOrders();
}
