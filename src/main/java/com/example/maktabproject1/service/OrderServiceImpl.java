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
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserServiceImpl userServiceImpl,
                            SubServiceServiceImpl subServiceServiceImpl,
                            SpecialistServiceImpl specialistServiceImpl) {
        this.orderRepository = orderRepository;
        this.userServiceImpl = userServiceImpl;
        this.subServiceServiceImpl = subServiceServiceImpl;
        this.specialistServiceImpl = specialistServiceImpl;
    }

    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        userServiceImpl.getUserById(orderEntity.getCustomer().getId());
        subServiceServiceImpl.getSubServiceById(orderEntity.getSubService().getId());
        if (orderEntity.getSpecialist() != null) {
            specialistServiceImpl.getSpecialistById(orderEntity.getSpecialist().getId());
        }
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity updateOrder(Long orderId, OrderEntity orderDetailsEntity) {
        OrderEntity existingOrder = getOrderById(orderId);
        existingOrder.setCustomer(userServiceImpl.getUserById(orderDetailsEntity.getCustomer().getId()));
        existingOrder.setSubService(subServiceServiceImpl.getSubServiceById(orderDetailsEntity.getSubService().getId()));
        existingOrder.setDescription(orderDetailsEntity.getDescription());
        existingOrder.setSuggestedPrice(orderDetailsEntity.getSuggestedPrice());
        existingOrder.setOrderDate(orderDetailsEntity.getOrderDate());
        existingOrder.setExecutionDate(orderDetailsEntity.getExecutionDate());
        existingOrder.setAddress(orderDetailsEntity.getAddress());
        existingOrder.setStatus(orderDetailsEntity.getStatus());
        if (orderDetailsEntity.getSpecialist() != null) {
            existingOrder.setSpecialist(specialistServiceImpl.getSpecialistById(orderDetailsEntity.getSpecialist().getId()));
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
