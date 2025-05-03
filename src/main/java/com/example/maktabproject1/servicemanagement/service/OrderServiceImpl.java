package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.OrderDto;
import com.example.maktabproject1.servicemanagement.dto.UpdateOrderDto;
import com.example.maktabproject1.usermanagement.entity.SpecialistEntity;
import com.example.maktabproject1.common.exception.ResponseNotFoundException;
import com.example.maktabproject1.servicemanagement.repository.OfferRepository;
import com.example.maktabproject1.servicemanagement.repository.OrderRepository;
import com.example.maktabproject1.servicemanagement.entity.OfferEntity;
import com.example.maktabproject1.servicemanagement.entity.OrderEntity;
import com.example.maktabproject1.servicemanagement.entity.OrderStatusType;
import com.example.maktabproject1.common.exception.InvalidOrderStatusException;
import com.example.maktabproject1.usermanagement.service.SpecialistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.maktabproject1.usermanagement.service.UserService;

import java.math.BigDecimal;
import java.time.Duration;
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
    public OrderDto updateOrder(Long orderId, UpdateOrderDto dto) {
        OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseNotFoundException("Order not found with id: " + orderId));
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

    private OrderEntity mapDtoToEntity(UpdateOrderDto dto) {
        OrderEntity entity = new OrderEntity();
        if (dto == null) {
            throw new IllegalArgumentException("Update Order DTO cannot be null");
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



    @Override
    public void startOrder(Long orderId) {
        OrderEntity order = getOrderEntityById(orderId);

        if (order.isFinished()) {
            throw new IllegalStateException("This order is already finished.");
        }

        if (order.getStartTime() != null) {
            throw new IllegalStateException("This order has already started.");
        }

        if (order.getAcceptedOffer() != null) {
            order.setProposedDuration(order.getProposedDuration());
        }

        order.setStartTime(LocalDateTime.now());
        order.setStatus(OrderStatusType.STARTED);
        orderRepository.save(order);
        log.info("Order with ID {} has started.", orderId);
    }

    @Override
    public void finishOrder(Long orderId) {
        OrderEntity order = getOrderEntityById(orderId);

        if (order.isFinished()) {
            throw new IllegalStateException("Order is already finished.");
        }

        if (order.getStartTime() == null) {
            throw new IllegalStateException("Order hasn't started yet.");
        }

        order.setEndTime(LocalDateTime.now());
        order.setFinished(true);
        order.setStatus(OrderStatusType.FINISHED);

        Duration actualDuration = Duration.between(order.getStartTime(), order.getEndTime());
        Duration allowedDuration = order.getProposedDuration();

        if (allowedDuration != null && actualDuration.compareTo(allowedDuration) > 0) {
            long extraHours = actualDuration.minus(allowedDuration).toHours();

            SpecialistEntity specialist = order.getSpecialist();
            if (specialist != null) {
                BigDecimal currentRating = specialist.getRating() != null ? specialist.getRating() : BigDecimal.TEN;
                BigDecimal penaltyPercentage = BigDecimal.valueOf(extraHours * 5);
                BigDecimal penaltyAmount = currentRating.multiply(penaltyPercentage).divide(BigDecimal.valueOf(100));

                BigDecimal updatedRating = currentRating.subtract(penaltyAmount).max(BigDecimal.ZERO);
                specialist.setRating(updatedRating);

                specialistService.save(specialist);
            }
        }

        orderRepository.save(order);
        log.info("Order with ID {} has been finished.", orderId);
    }

    private OrderEntity getOrderEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Order not found: " + id));
    }
}
