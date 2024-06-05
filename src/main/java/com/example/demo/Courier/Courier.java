package com.example.demo.Courier;

import com.example.demo.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@Table(name = "courier")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    private String name;

    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY)
    @Setter
    private List<Order> orders;
}

