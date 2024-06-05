package com.example.demo.Product;

import com.example.demo.Substation.SubstationProduct;
import com.example.demo.Substation.SubstationProductDTO;
import com.example.demo.Substation.SubstationProductRepository;
import com.example.demo.Substation.SubstationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SubstationProductRepository substationProductRepository;
    private final SubstationRepository substationRepository;

    public ProductController(ProductService productService, SubstationProductRepository substationProductRepository, SubstationRepository substationRepository) {
        this.productService = productService;
        this.substationProductRepository = substationProductRepository;
        this.substationRepository = substationRepository;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return ResponseEntity.ok(convertToDTO(product));
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        return convertToDTO(productService.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDetails) {
        Product updatedProduct = productService.updateProduct(id, convertToEntity(productDetails));
        return ResponseEntity.ok(convertToDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setSubstationProducts(product.getSubstationProducts().stream().map(substationProduct -> {
            SubstationProductDTO substationProductDTO = new SubstationProductDTO();
            substationProductDTO.setId(substationProduct.getId());
            substationProductDTO.setSubstationId(substationProduct.getSubstation().getId());
            substationProductDTO.setSubstationName(substationProduct.getSubstation().getName());
            substationProductDTO.setProductId(substationProduct.getProduct().getId());
            substationProductDTO.setProductName(substationProduct.getProduct().getName());
            substationProductDTO.setQuantity(substationProduct.getQuantity());
            return substationProductDTO;
        }).collect(Collectors.toList()));
        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product;

        if (productDTO.getId() != null) {
            product = productService.getProductById(productDTO.getId()).orElse(new Product());
        } else {
            product = new Product();
        }

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        if (productDTO.getSubstationProducts() != null) {
            List<SubstationProduct> substationProducts = productDTO.getSubstationProducts().stream().map(dto -> {
                SubstationProduct substationProduct;
                if (dto.getId() != null) {
                    substationProduct = substationProductRepository.findById(dto.getId()).orElse(new SubstationProduct());
                } else {
                    substationProduct = new SubstationProduct();
                }
                substationProduct.setId(dto.getId());
                substationProduct.setQuantity(dto.getQuantity());
                // Assuming you have methods to get Substation and Product by their IDs
                substationProduct.setSubstation(substationRepository.findById(dto.getSubstationId()).orElse(null));
                substationProduct.setProduct(product);
                return substationProduct;
            }).collect(Collectors.toList());
            product.setSubstationProducts(substationProducts);
        }

        return product;
    }
}
