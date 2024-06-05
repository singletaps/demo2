package com.example.demo.Product;

import com.example.demo.Substation.SubstationProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private List<SubstationProductDTO> substationProducts;
}
