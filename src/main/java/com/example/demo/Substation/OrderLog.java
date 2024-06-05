package com.example.demo.Substation;

import com.example.demo.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class OrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private LogStatus status;
    @Getter
    @Setter
    private Long order_id;

    @Getter
    @Setter
    private Long product_id;

    @Getter
    @Setter
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substation_id")
    private Substation substation;

    private Date timestamp;

}
