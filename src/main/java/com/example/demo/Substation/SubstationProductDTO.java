package com.example.demo.Substation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubstationProductDTO {
    private Long id;
    private Long substationId;
    private String substationName;
    private Long productId;
    private String productName;
    private int quantity;
}
