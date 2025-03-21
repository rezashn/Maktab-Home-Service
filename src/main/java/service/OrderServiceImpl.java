package service;

import entity.Order;
import exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.OrderRepository;

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

    public Order createOrder(Order order) {
        userServiceImpl.getUserById(order.getCustomer().getId());
        subServiceServiceImpl.getSubServiceById(order.getSubService().getId());
        if (order.getSpecialist() != null) {
            specialistServiceImpl.getSpecialistById(order.getSpecialist().getId());
        }
        return orderRepository.save(order);
    }

    public Order updateOrder(Long orderId, Order orderDetails) {
        Order existingOrder = getOrderById(orderId);
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

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResponseNotFoundException("ORDER NOT FOUND");
        }
        orderRepository.deleteById(orderId);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResponseNotFoundException("ORDER NOT FOUND"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
