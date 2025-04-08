package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.OrderDto;
import com.example.maktabproject1.entity.OrderStatusType;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDTO);

    OrderDto updateOrder(Long orderId, OrderDto orderDTO);

    void deleteOrder(Long orderId);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getAllOrders();

    OrderDto updateOrderStatus(Long orderId, OrderStatusType newStatus);

    OrderDto assignSpecialistToOrder(Long orderId, Long specialistId);
}