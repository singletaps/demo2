package com.example.demo.Substation;

import com.example.demo.Location.Location;
import com.example.demo.Order.Order;
import com.example.demo.Product.Product;
import com.example.demo.Schedule.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Substation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    Location location;
    // Getters and Setters
    @OneToMany(mappedBy = "substation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<SubstationProduct> substationProducts;

    @OneToMany(mappedBy = "substation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderLog> logs;

    @OneToMany(mappedBy = "substation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "substation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> waiting;
}