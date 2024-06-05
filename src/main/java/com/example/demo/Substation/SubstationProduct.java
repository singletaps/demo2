package com.example.demo.Substation;

import com.example.demo.Product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SubstationProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "substation_id", nullable = false)
    private Substation substation;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Getter
    @Setter
    private int quantity;
}
