package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.OrderDto;
import com.example.maktabproject1.entity.OrderStatusEntity;
import com.example.maktabproject1.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDTO) {
        try {
            log.info("Attempting to create order: {}", orderDTO);
            OrderDto createdOrder = orderService.createOrder(orderDTO);
            log.info("Order created successfully: {}", createdOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            log.error("Error creating order: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDTO) {
        try {
            log.info("Attempting to update order with ID: {}, data: {}", orderId, orderDTO);
            OrderDto updatedOrder = orderService.updateOrder(orderId, orderDTO);
            log.info("Order updated successfully: {}", updatedOrder);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            log.error("Error updating order: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        try {
            log.info("Attempting to delete order with ID: {}", orderId);
            orderService.deleteOrder(orderId);
            log.info("Order deleted successfully: {}", orderId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting order: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        try {
            log.info("Fetching order by ID: {}", orderId);
            OrderDto order = orderService.getOrderById(orderId);
            log.info("Order found: {}", order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("Error fetching order: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        try {
            log.info("Fetching all orders.");
            List<OrderDto> orders = orderService.getAllOrders();
            log.info("Found {} orders.", orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error fetching all orders: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusEntity newStatus) {
        try {
            log.info("Attempting to update order status with ID: {}, new status: {}", orderId, newStatus);
            OrderDto updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
            log.info("Order status updated successfully: {}", updatedOrder);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            log.error("Error updating order status: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{orderId}/specialist/{specialistId}")
    public ResponseEntity<OrderDto> assignSpecialistToOrder(@PathVariable Long orderId, @PathVariable Long specialistId) {
        try {
            log.info("Attempting to assign specialist to order with ID: {}, specialist ID: {}", orderId, specialistId);
            OrderDto updatedOrder = orderService.assignSpecialistToOrder(orderId, specialistId);
            log.info("Specialist assigned to order successfully: {}", updatedOrder);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            log.error("Error assigning specialist to order: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}