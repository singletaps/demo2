package com.example.demo.Order;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders().stream().map(orderService::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping
    public void addOrder(@RequestBody OrderDTO orderDTO) {
        orderService.addOrder(orderService.convertToEntity(orderDTO));
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.convertToDTO(orderService.getOrderById(id));
    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        orderService.updateOrder(id, orderService.convertToEntity(orderDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/status/{status}")
    public List<OrderDTO> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status).stream().map(orderService::convertToDTO).collect(Collectors.toList());
    }
}
