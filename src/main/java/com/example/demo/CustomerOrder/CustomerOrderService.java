package com.example.demo.CustomerOrder;

import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerRepository;
import com.example.demo.Order.Order;
import com.example.demo.Order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<CustomerOrderDTO> findAll() {
        return customerOrderRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<CustomerOrderDTO> findById(Long id) {
        return customerOrderRepository.findById(id).map(this::convertToDTO);
    }

    public CustomerOrderDTO save(CustomerOrderDTO customerOrderDTO) {
        CustomerOrder customerOrder = convertToEntity(customerOrderDTO);
        CustomerOrder savedCustomerOrder = customerOrderRepository.save(customerOrder);
        return convertToDTO(savedCustomerOrder);
    }

    public void deleteById(Long id) {
        customerOrderRepository.deleteById(id);
    }

    private CustomerOrderDTO convertToDTO(CustomerOrder customerOrder) {
        CustomerOrderDTO dto = new CustomerOrderDTO();
        dto.setId(customerOrder.getId());
        dto.setCustomerId(customerOrder.getCustomer().getId());
        dto.setOrderId(customerOrder.getOrder().getId());
        return dto;
    }

    private CustomerOrder convertToEntity(CustomerOrderDTO dto) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(dto.getId());

        Optional<Customer> customer = customerRepository.findById(dto.getCustomerId());
        customer.ifPresent(customerOrder::setCustomer);

        Optional<Order> order = orderRepository.findById(dto.getOrderId());
        order.ifPresent(customerOrder::setOrder);

        return customerOrder;
    }
}
