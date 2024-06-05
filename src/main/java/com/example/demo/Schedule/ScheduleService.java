package com.example.demo.Schedule;

import com.example.demo.Location.Location;
import com.example.demo.Location.LocationDistance;
import com.example.demo.Location.LocationDistanceRepository;
import com.example.demo.Order.Order;
import com.example.demo.Order.OrderRepository;
import com.example.demo.Product.Product;
import com.example.demo.Substation.*;
import com.example.demo.Order.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private SubstationRepository substationRepository;
    @Autowired
    private SubstationProductRepository substationProductRepository;
    @Autowired
    private OrderLogRepository orderLogRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private LocationDistanceRepository locationDistanceRepository;

    private static final int WARNING_THRESHOLD = 200;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Schedule createOrUpdateSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Transactional
    public Schedule scheduleOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Substation startSubstation = order.getStartSubstation();
        Product product = order.getProduct();

        checkAndTransferProduct(startSubstation, product);

        SubstationProduct substationProduct = substationProductRepository.findBySubstationAndProduct(startSubstation, product)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in substation"));

        if (substationProduct.getQuantity() >= 1) {
            substationProduct.setQuantity(substationProduct.getQuantity() - 1);
            substationProductRepository.save(substationProduct);
            logOrder(order.getId(), startSubstation, product, LogStatus.OUT, 1);
        } else {
            throw new IllegalStateException("Insufficient product quantity");
        }

        Schedule schedule = new Schedule();
        order.setStatus(OrderStatus.SCHEDULING);
        order.setOrderDate(new Date());
        orderRepository.save(order);
        schedule.setType(ScheduleStatus.WAITING);
        schedule.setOrder(order);
        startSubstation.getWaiting().add(schedule);

        return schedule;
    }

    private void checkAndTransferProduct(Substation substation, Product product) {
        SubstationProduct substationProduct = substationProductRepository.findBySubstationAndProduct(substation, product)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in substation"));

        if (substationProduct.getQuantity() < WARNING_THRESHOLD) {
            Substation nearestSubstation = findNearestSubstationWithProduct(substation, product);
            if (nearestSubstation != null) {
                SubstationProduct nearestSubstationProduct = substationProductRepository.findBySubstationAndProduct(nearestSubstation, product)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found in nearest substation"));

                int availableQuantity = nearestSubstationProduct.getQuantity() - WARNING_THRESHOLD;
                int transferQuantity = Math.min(WARNING_THRESHOLD - substationProduct.getQuantity(), availableQuantity);

                if (transferQuantity > 0) {
                    nearestSubstationProduct.setQuantity(nearestSubstationProduct.getQuantity() - transferQuantity);
                    substationProduct.setQuantity(substationProduct.getQuantity() + transferQuantity);

                    substationProductRepository.save(nearestSubstationProduct);
                    substationProductRepository.save(substationProduct);

                    logOrderTransfer(nearestSubstation, substation, product, transferQuantity);
                }
            }
        }
    }

    private Substation findNearestSubstationWithProduct(Substation startSubstation, Product product) {
        Location startLocation = startSubstation.getLocation();
        List<LocationDistance> distances = locationDistanceRepository.findByLocation(startLocation);
        return distances.stream()
                .sorted(Comparator.comparingDouble(LocationDistance::getDistance))
                .map(distance -> substationRepository.findByLocation(distance.getTargetLocation()))
                .filter(substation -> substation.getSubstationProducts().stream()
                        .anyMatch(sp -> sp.getProduct().equals(product) && sp.getQuantity() > 0))
                .findFirst()
                .orElse(null);
    }

    private void logOrderTransfer(Substation fromSubstation, Substation toSubstation, Product product, int quantity) {
        logOrder(null, fromSubstation, product, LogStatus.OUT, quantity);
        logOrder(null, toSubstation, product, LogStatus.IN, quantity);
    }

    private void logOrder(Long orderId, Substation substation, Product product, LogStatus status, int quantity) {
        OrderLog orderLog = new OrderLog();
        orderLog.setOrder_id(orderId);
        orderLog.setSubstation(substation);
        orderLog.setStatus(status);
        orderLog.setTimestamp(new Date());
        orderLog.setProduct_id(product.getId());
        orderLog.setQuantity(quantity);
        orderLogRepository.save(orderLog);
    }
}
