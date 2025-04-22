package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.OrderDto;
import com.example.maktabproject1.servicemanagement.dto.UpdateOrderDto;
import com.example.maktabproject1.servicemanagement.entity.OfferEntity;
import com.example.maktabproject1.servicemanagement.entity.OrderStatusType;
import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto dto);

    OrderDto updateOrder(Long orderId, UpdateOrderDto dto);

    void deleteOrder(Long orderId);

    OrderDto getOrderById(Long orderId);

    void startOrder(Long orderId);

    void finishOrder(Long orderId);

    List<OrderDto> getAllOrders();

    OrderDto updateOrderStatus(Long orderId, OrderStatusType newStatus);

    OrderDto assignSpecialistToOrder(Long orderId, Long specialistId);

    OfferEntity getAcceptedOffer(Long orderId);

    List<OfferEntity> getAllOffersOfOrder(Long orderId);
}