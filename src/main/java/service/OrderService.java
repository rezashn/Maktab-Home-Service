package service;

import entity.Order;
import exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {


        private final OrderRepository orderRepository;
        private final UserService userService;
        private final SubServiceService subServiceService;
        private final SpecialistService specialistService;

        @Autowired
        public OrderService(OrderRepository orderRepository, UserService userService, SubServiceService subServiceService, SpecialistService specialistService) {
            this.orderRepository = orderRepository;
            this.userService = userService;
            this.subServiceService = subServiceService;
            this.specialistService = specialistService;
        }

        public Order createOrder(Order order) {
            userService.getUserById(order.getCustomer().getId());
            subServiceService.getSubServiceById(order.getSubService().getId());
            if (order.getSpecialist() != null) {
                specialistService.getSpecialistById(order.getSpecialist().getId());
            }
            return orderRepository.save(order);
        }

        public Order updateOrder(Long orderId, Order orderDetails) {
            Order existingOrder = getOrderById(orderId);
            existingOrder.setCustomer(userService.getUserById(orderDetails.getCustomer().getId()));
            existingOrder.setSubService(subServiceService.getSubServiceById(orderDetails.getSubService().getId()));
            existingOrder.setDescription(orderDetails.getDescription());
            existingOrder.setSuggestedPrice(orderDetails.getSuggestedPrice());
            existingOrder.setOrderDate(orderDetails.getOrderDate());
            existingOrder.setExecutionDate(orderDetails.getExecutionDate());
            existingOrder.setAddress(orderDetails.getAddress());
            existingOrder.setStatus(orderDetails.getStatus());
            if (orderDetails.getSpecialist() != null) {
                existingOrder.setSpecialist(specialistService.getSpecialistById(orderDetails.getSpecialist().getId()));
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
