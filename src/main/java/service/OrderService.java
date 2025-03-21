package service;

import entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order updateOrder(Long orderId, Order orderDetails);
    void deleteOrder(Long orderId);
    Order getOrderById(Long orderId);
    List<Order> getAllOrders();
}
