package com.example.demo.Substation;

import com.example.demo.Location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long> {
    Substation findByLocation(Location location);
}

