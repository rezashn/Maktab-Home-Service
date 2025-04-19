package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.OrderDto;
import com.example.maktabproject1.entity.OfferEntity;
import com.example.maktabproject1.entity.OrderEntity;
import com.example.maktabproject1.entity.OrderStatusType;
import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.exception.InvalidOrderStatusException;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.OfferRepository;
import com.example.maktabproject1.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;
    private final UserService userService;
    private final SubServiceService subServiceService;
    private final SpecialistService specialistService;
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OfferRepository offerRepository,
                            UserService userService,
                            SubServiceService subServiceService,
                            SpecialistService specialistService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.subServiceService = subServiceService;
        this.specialistService = specialistService;
        this.offerRepository = offerRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        OrderEntity entity = mapDtoToEntity(dto);
        entity.setStatus(OrderStatusType.WAITING_FOR_OFFER);
        entity.setOrderDate(LocalDateTime.now());
        OrderEntity savedEntity = orderRepository.save(entity);
        log.info("Order created with ID: {}", savedEntity.getId());
        return mapEntityToDto(savedEntity);
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto dto) {
        OrderEntity existingOrder = getOrderEntityById(orderId);
        OrderEntity updatedOrder = mapDtoToEntity(dto);
        updatedOrder.setId(orderId);
        OrderEntity savedUpdatedEntity = orderRepository.save(updatedOrder);
        log.info("Order updated with ID: {}", savedUpdatedEntity.getId());
        return mapEntityToDto(savedUpdatedEntity);
    }

    @Override
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
    public OrderDto updateOrderStatus(Long orderId, OrderStatusType newStatus) {
        OrderEntity order = getOrderEntityById(orderId);
        order.setStatus(newStatus);
        OrderEntity updatedOrder = orderRepository.save(order);
        log.info("Order status updated to {} for order ID: {}", newStatus, orderId);
        return mapEntityToDto(updatedOrder);
    }

    @Override
    public OrderDto assignSpecialistToOrder(Long orderId, Long specialistId) {
        OrderEntity order = getOrderEntityById(orderId);
        SpecialistEntity specialist = specialistService.getSpecialistEntityById(specialistId);
        order.setSpecialist(specialist);
        order.setStatus(OrderStatusType.SPECIALIST_ON_THE_WAY);
        OrderEntity updatedOrder = orderRepository.save(order);
        log.info("Specialist assigned to order ID: {}", orderId);
        return mapEntityToDto(updatedOrder);
    }

    @Override
    public OfferEntity getAcceptedOffer(Long orderId) {
        OrderEntity order = getOrderEntityById(orderId);
        return order.getAcceptedOffer();
    }

    @Override
    public List<OfferEntity> getAllOffersOfOrder(Long orderId) {
        OrderEntity order = getOrderEntityById(orderId);
        return order.getOffers();
    }

    private OrderEntity mapDtoToEntity(OrderDto dto) {
        OrderEntity entity = new OrderEntity();
        if (dto == null) {
            throw new IllegalArgumentException("Order DTO cannot be null");
        }
        entity.setId(dto.getId());
        entity.setCustomer(userService.getUserEntityById(dto.getCustomerId()));
        entity.setDescription(dto.getDescription());
        entity.setSuggestedPrice(dto.getProposedPrice());
        entity.setOrderDate(dto.getOrderDate());
        entity.setAddress(dto.getAddress());
        if (dto.getOrderStatus() == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }
        try {
            entity.setStatus(OrderStatusType.valueOf(dto.getOrderStatus()));
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid order status: " + dto.getOrderStatus());
        }
        if (dto.getSpecialistId() != null) {
            entity.setSpecialist(specialistService.getSpecialistEntityById(dto.getSpecialistId()));
        }
        return entity;
    }

    private OrderDto mapEntityToDto(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        if (entity == null) {
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
