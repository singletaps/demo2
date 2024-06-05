package com.example.demo.Order;

import com.example.demo.Courier.Courier;
import com.example.demo.Customer.Customer;
import com.example.demo.CustomerOrder.CustomerOrder;
import com.example.demo.Product.Product;
import com.example.demo.Substation.OrderLog;
import com.example.demo.Substation.Substation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`") // 这里使用反引号来包围表名
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Product product;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private OrderStatus status;

    @Getter
    @Setter
    private Date orderDate;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_order_id")
    @JsonBackReference
    private CustomerOrder customerorder;


    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substation_id")
    private Substation startSubstation;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private Courier courier;

}
