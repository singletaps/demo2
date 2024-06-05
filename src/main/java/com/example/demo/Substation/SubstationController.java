package com.example.demo.Substation;

import com.example.demo.Product.Product;
import com.example.demo.Product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/substation")
public class SubstationController {
    @Autowired
    private SubstationService substationService;

    @GetMapping
    public List<Substation> getAllSubstations() {
        return substationService.getAllSubstations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Substation> getSubstationById(@PathVariable Long id) {
        Substation substation = substationService.getSubstationById(id).orElseThrow(() -> new RuntimeException("Substation not found"));
        return ResponseEntity.ok(substation);
    }

    @PostMapping
    public Substation createSubstation(@RequestBody Substation substation) {
        return substationService.saveSubstation(substation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Substation> updateSubstation(@PathVariable Long id, @RequestBody Substation substationDetails) {
        Substation updatedSubstation = substationService.updateSubstation(id, substationDetails);
        return ResponseEntity.ok(updatedSubstation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubstation(@PathVariable Long id) {
        substationService.deleteSubstation(id);
        return ResponseEntity.noContent().build();
    }

    private SubstationProductDTO convertToDTO(SubstationProduct substationProduct) {
        SubstationProductDTO SubstationProductDTO = new SubstationProductDTO();
        SubstationProductDTO.setId(substationProduct.getId());
        SubstationProductDTO.setSubstationName(substationProduct.getSubstation().getName());
        SubstationProductDTO.setProductName(substationProduct.getProduct().getName());
        SubstationProductDTO.setProductId(substationProduct.getProduct().getId());
        SubstationProductDTO.setSubstationId(substationProduct.getSubstation().getId());
        SubstationProductDTO.setQuantity(substationProduct.getQuantity());
        return SubstationProductDTO;
    }

    private SubstationProduct convertToEntity(SubstationProductDTO substationProductDTO) {
        SubstationProduct substationProduct = new SubstationProduct();
        substationProduct.setId(substationProductDTO.getId());
        return substationProduct;
    }

}

