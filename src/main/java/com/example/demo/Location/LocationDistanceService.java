package com.example.demo.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationDistanceService {
    @Autowired
    private LocationDistanceRepository locationDistanceRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<LocationDistance> findAllLocationDistances() {
        return locationDistanceRepository.findAll();
    }

    public Optional<LocationDistance> findLocationDistanceById(Long id) {
        return locationDistanceRepository.findById(id);
    }

    public LocationDistance saveLocationDistance(LocationDistance locationDistance) {
        return locationDistanceRepository.save(locationDistance);
    }

    public void deleteLocationDistance(Long id) {
        locationDistanceRepository.deleteById(id);
    }

    public LocationDistance findDistanceBetweenLocations(Location location, Location targetLocation) {
        return locationDistanceRepository.findByLocationAndTargetLocation(location, targetLocation);
    }

    public LocationDistance createOrUpdateLocationDistance(Long locationId, Long targetLocationId, double distance) {
        Optional<Location> locationOpt = locationRepository.findById(locationId);
        Optional<Location> targetLocationOpt = locationRepository.findById(targetLocationId);

        if (locationOpt.isPresent() && targetLocationOpt.isPresent()) {
            Location location = locationOpt.get();
            Location targetLocation = targetLocationOpt.get();

            LocationDistance locationDistance = locationDistanceRepository.findByLocationAndTargetLocation(location, targetLocation);
            if (locationDistance == null) {
                locationDistance = new LocationDistance();
                locationDistance.setLocation(location);
                locationDistance.setTargetLocation(targetLocation);
            }

            locationDistance.setDistance(distance);
            return locationDistanceRepository.save(locationDistance);
        } else {
            throw new IllegalArgumentException("Invalid location or target location ID");
        }
    }
}
