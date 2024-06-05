package com.example.demo.Invoice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    private String invoiceNumber;
    @Getter
    @Setter
    private String status;

    // Getters and Setters
}
