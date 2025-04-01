package com.example.maktabproject1.service;

import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.OrderRepository;
import com.example.maktabproject1.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final UserServiceImpl userServiceImpl;
    private final SubServiceServiceImpl subServiceServiceImpl;
    private final SpecialistServiceImpl specialistServiceImpl;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImpl userServiceImpl, SubServiceServiceImpl subServiceServiceImpl, SpecialistServiceImpl specialistServiceImpl) {
        this.orderRepository = orderRepository;
        this.userServiceImpl = userServiceImpl;
        this.subServiceServiceImpl = subServiceServiceImpl;
        this.specialistServiceImpl = specialistServiceImpl;
    }

    @Override
    public OrderEntity createOrder(OrderEntity order) {
        userServiceImpl.getUserById(order.getCustomer().getId());
        subServiceServiceImpl.getSubServiceById(order.getSubService().getId());
        if (order.getSpecialist() != null) {
            specialistServiceImpl.getSpecialistById(order.getSpecialist().getId());
        }
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity updateOrder(Long orderId, OrderEntity orderDetails) {
        OrderEntity existingOrder = getOrderById(orderId);
        existingOrder.setCustomer(userServiceImpl.getUserById(orderDetails.getCustomer().getId()));
        existingOrder.setSubService(subServiceServiceImpl.getSubServiceById(orderDetails.getSubService().getId()));
        existingOrder.setDescription(orderDetails.getDescription());
        existingOrder.setSuggestedPrice(orderDetails.getSuggestedPrice());
        existingOrder.setOrderDate(orderDetails.getOrderDate());
        existingOrder.setExecutionDate(orderDetails.getExecutionDate());
        existingOrder.setAddress(orderDetails.getAddress());
        existingOrder.setStatus(orderDetails.getStatus());
        if (orderDetails.getSpecialist() != null) {
            existingOrder.setSpecialist(specialistServiceImpl.getSpecialistById(orderDetails.getSpecialist().getId()));
        } else {
            existingOrder.setSpecialist(null);
        }
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResponseNotFoundException("ORDER NOT FOUND");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResponseNotFoundException("ORDER NOT FOUND"));
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}
