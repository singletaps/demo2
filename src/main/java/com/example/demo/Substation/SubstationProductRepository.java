package com.example.demo.Substation;

import com.example.demo.Location.Location;
import com.example.demo.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubstationProductRepository extends JpaRepository<SubstationProduct, Long> {
    Optional<SubstationProduct> findBySubstationAndProduct(Substation substation, Product product);
}
