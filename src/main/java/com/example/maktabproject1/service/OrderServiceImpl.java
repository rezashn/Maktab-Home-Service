package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.OrderDto;
import com.example.maktabproject1.entity.OrderEntity;
import com.example.maktabproject1.entity.OrderStatusEntity;
import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final SubServiceService subServiceService;
    private final SpecialistService specialistService;
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserService userService,
                            SubServiceService subServiceService,
                            SpecialistService specialistService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.subServiceService = subServiceService;
        this.specialistService = specialistService;
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto dto) {
        OrderEntity entity = mapDtoToEntity(dto);
        entity.setStatus(OrderStatusEntity.WAITING_FOR_OFFER);
        entity.setOrderDate(LocalDateTime.now());
        OrderEntity savedEntity = orderRepository.save(entity);
        log.info("Order created with ID: {}", savedEntity.getId());
        return mapEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long orderId, OrderDto dto) {
        OrderEntity existingOrder = getOrderEntityById(orderId);
        OrderEntity updatedOrder = mapDtoToEntity(dto);
        updatedOrder.setId(orderId);
        OrderEntity savedUpdatedEntity = orderRepository.save(updatedOrder);
        log.info("Order updated with ID: {}", savedUpdatedEntity.getId());
        return mapEntityToDto(savedUpdatedEntity);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResponseNotFoundException("ORDER NOT FOUND");
        }
        orderRepository.deleteById(orderId);
        log.info("Order deleted with ID: {}", orderId);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return mapEntityToDto(getOrderEntityById(orderId));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatusEntity newStatus) {
        OrderEntity order = getOrderEntityById(orderId);
        order.setStatus(newStatus);
        OrderEntity updatedOrder = orderRepository.save(order);
        log.info("Order status updated to {} for order ID: {}", newStatus, orderId);
        return mapEntityToDto(updatedOrder);
    }

    @Override
    @Transactional
    public OrderDto assignSpecialistToOrder(Long orderId, Long specialistId) {
        OrderEntity order = getOrderEntityById(orderId);
        SpecialistEntity specialist = specialistService.getSpecialistEntityById(specialistId);
        order.setSpecialist(specialist);
        order.setStatus(OrderStatusEntity.SPECIALIST_ON_THE_WAY);
        OrderEntity updatedOrder = orderRepository.save(order);
        log.info("Specialist assigned to order ID: {}", orderId);
        return mapEntityToDto(updatedOrder);
    }

    private OrderEntity mapDtoToEntity(OrderDto dto) {
        OrderEntity entity = new OrderEntity();
        if (dto == null) {
            throw new IllegalArgumentException("Order DTO cannot be null");
        }
        entity.setId(dto.getId());
        entity.setCustomer(userService.getUserEntityById(dto.getCustomerId()));
        entity.setSubService(subServiceService.getSubServiceEntityById(dto.getSubServiceId()));
        entity.setDescription(dto.getDescription());
        entity.setSuggestedPrice(dto.getProposedPrice());
        entity.setOrderDate(dto.getOrderDate());
        entity.setAddress(dto.getAddress());
        if(dto.getOrderStatus() == null){
            throw new IllegalArgumentException("Order status cannot be null");
        }
        try {
            entity.setStatus(OrderStatusEntity.valueOf(dto.getOrderStatus()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + dto.getOrderStatus());
        }
        if (dto.getSpecialistId() != null) {
            entity.setSpecialist(specialistService.getSpecialistEntityById(dto.getSpecialistId()));
        }
        return entity;
    }

    private OrderDto mapEntityToDto(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        if(entity == null){
            throw new IllegalArgumentException("Order Entity cannot be null");
        }
        dto.setId(entity.getId());
        dto.setCustomerId(entity.getCustomer().getId());
        dto.setSubServiceId(entity.getSubService().getId());
        dto.setDescription(entity.getDescription());
        dto.setProposedPrice(entity.getSuggestedPrice());
        dto.setOrderDate(entity.getOrderDate());
        dto.setAddress(entity.getAddress());
        dto.setOrderStatus(entity.getStatus().name());
        if (entity.getSpecialist() != null) {
            dto.setSpecialistId(entity.getSpecialist().getId());
        }
        return dto;
    }

    private OrderEntity getOrderEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Order not found: " + id));
    }
}