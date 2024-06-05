package com.example.demo.CustomerOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrderDTO {
    private Long id;
    private Long customerId;
    private Long orderId;
}
