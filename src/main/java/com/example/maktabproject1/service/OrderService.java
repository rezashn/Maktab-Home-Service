package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.OrderDto;
import com.example.maktabproject1.entity.OfferEntity;
import com.example.maktabproject1.entity.OrderStatusType;
import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto dto);

    OrderDto updateOrder(Long orderId, OrderDto dto);

    void deleteOrder(Long orderId);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getAllOrders();

    OrderDto updateOrderStatus(Long orderId, OrderStatusType newStatus);

    OrderDto assignSpecialistToOrder(Long orderId, Long specialistId);

    OfferEntity getAcceptedOffer(Long orderId);

    List<OfferEntity> getAllOffersOfOrder(Long orderId);
}