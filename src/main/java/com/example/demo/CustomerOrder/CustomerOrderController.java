package com.example.demo.CustomerOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer-orders")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping
    public List<CustomerOrderDTO> getAllCustomerOrders() {
        return customerOrderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> getCustomerOrderById(@PathVariable Long id) {
        Optional<CustomerOrderDTO> customerOrder = customerOrderService.findById(id);
        return customerOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CustomerOrderDTO createCustomerOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
        return customerOrderService.save(customerOrderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> updateCustomerOrder(@PathVariable Long id, @RequestBody CustomerOrderDTO customerOrderDTO) {
        if (customerOrderService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        customerOrderDTO.setId(id);
        CustomerOrderDTO updatedCustomerOrder = customerOrderService.save(customerOrderDTO);
        return ResponseEntity.ok(updatedCustomerOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable Long id) {
        if (customerOrderService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        customerOrderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
