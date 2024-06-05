package com.example.demo.Substation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubstationService {

    @Autowired
    private SubstationRepository substationRepository;

    public List<Substation> getAllSubstations() {
        return substationRepository.findAll();
    }

    public Optional<Substation> getSubstationById(Long id) {
        return substationRepository.findById(id);
    }

    public Substation saveSubstation(Substation substation) {
        return substationRepository.save(substation);
    }

    public Substation updateSubstation(Long id, Substation substationDetails) {
        Substation substation = substationRepository.findById(id).orElseThrow(() -> new RuntimeException("Substation not found"));
        substation.setName(substationDetails.getName());
        substation.setLocation(substationDetails.getLocation());
        return substationRepository.save(substation);
    }

    public void deleteSubstation(Long id) {
        Substation substation = substationRepository.findById(id).orElseThrow(() -> new RuntimeException("Substation not found"));
        substationRepository.delete(substation);
    }
}

