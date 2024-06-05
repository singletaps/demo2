package com.example.demo.Courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourierService {

    @Autowired
    private CourierRepository courierRepository;

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    public Optional<Courier> getCourierById(Long id) {
        return courierRepository.findById(id);
    }

    public Courier createCourier(Courier courier) {
        return courierRepository.save(courier);
    }

    public Courier updateCourier(Long id, Courier courierDetails) {
        Courier courier = courierRepository.findById(id).orElseThrow(() -> new RuntimeException("Courier not found"));

        courier.setName(courierDetails.getName());
        // 更新其他需要的字段

        return courierRepository.save(courier);
    }

    public void deleteCourier(Long id) {
        Courier courier = courierRepository.findById(id).orElseThrow(() -> new RuntimeException("Courier not found"));
        courierRepository.delete(courier);
    }
}
