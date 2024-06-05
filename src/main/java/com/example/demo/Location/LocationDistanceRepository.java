package com.example.demo.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationDistanceRepository extends JpaRepository<LocationDistance, Long> {
    LocationDistance findByLocationAndTargetLocation(Location location, Location targetLocation);
    List<LocationDistance> findByLocation(Location location);
}
