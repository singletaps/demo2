package com.example.demo.Order;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long productId;
    private String productName;
    private OrderStatus status;
    private Date orderDate;
    private Long customerId;
    private String customerName;
    private Long startSubstationId;
    private String startSubstationName;
    private Long courierId;
    private String courierName;
}


