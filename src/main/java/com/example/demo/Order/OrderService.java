package com.example.demo.Order;

import com.example.demo.Courier.Courier;
import com.example.demo.Courier.CourierRepository;
import com.example.demo.Customer.Customer;
import com.example.demo.CustomerOrder.CustomerOrder;
import com.example.demo.Product.Product;
import com.example.demo.Product.ProductRepository;
import com.example.demo.Substation.Substation;
import com.example.demo.Substation.SubstationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final SubstationRepository substationRepository;
    private final CourierRepository courierRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        SubstationRepository substationRepository, CourierRepository courierRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.substationRepository = substationRepository;
        this.courierRepository = courierRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void updateOrder(Long id, Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(id);
            orderRepository.save(order);
        }
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderDTO convertToDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setProductId(order.getProduct() != null ? order.getProduct().getId() : null);
        dto.setProductName(order.getProduct() != null ? order.getProduct().getName() : null);
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        dto.setCustomerId(order.getCustomerorder().getId() != null ? order.getCustomerorder().getId() : null);
        dto.setCustomerName(order.getCustomerorder().getCustomer().getName() != null ? order.getCustomerorder().getCustomer().getName() : null);
        dto.setStartSubstationId(order.getStartSubstation() != null ? order.getStartSubstation().getId() : null);
        dto.setStartSubstationName(order.getStartSubstation() != null ? order.getStartSubstation().getName() : null);
        dto.setCourierId(order.getCourier() != null ? order.getCourier().getId() : null);
        dto.setCourierName(order.getCourier() != null ? order.getCourier().getName() : null);
        return dto;
    }

    public Order convertToEntity(OrderDTO dto) {
        if (dto == null) return null;
        Order order;

        if (dto.getId() != null) {
            order = orderRepository.findById(dto.getId()).orElse(new Order());
        } else {
            order = new Order();
        }

        if (dto.getProductId() != null) {
            order.setProduct(productRepository.findById(dto.getProductId()).orElse(null));
        }

        order.setStatus(dto.getStatus());
        order.setOrderDate(dto.getOrderDate());

        if (dto.getCustomerId() != null) {
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setId(dto.getCustomerId());
            order.setCustomerorder(customerOrder);
        }

        if (dto.getStartSubstationId() != null) {
            order.setStartSubstation(substationRepository.findById(dto.getStartSubstationId()).orElse(null));
        }

        if (dto.getCourierId() != null) {
            order.setCourier(courierRepository.findById(dto.getCourierId()).orElse(null));
        }

        return order;
    }
}
