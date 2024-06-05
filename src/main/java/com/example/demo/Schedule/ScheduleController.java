package com.example.demo.Schedule;

import com.example.demo.Order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id)
                .map(schedule -> ResponseEntity.ok().body(schedule))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.createOrUpdateSchedule(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule scheduleDetails) {
        return scheduleService.getScheduleById(id)
                .map(schedule -> {
                    schedule.setType(scheduleDetails.getType());
                    schedule.setOrder(scheduleDetails.getOrder());
                    Schedule updatedSchedule = scheduleService.createOrUpdateSchedule(schedule);
                    return ResponseEntity.ok().body(updatedSchedule);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/order")
    public ResponseEntity<Schedule> handleOrderSchedule(@RequestBody Order order) {
        Schedule schedule = scheduleService.scheduleOrder(order.getId());
        return ResponseEntity.ok(schedule);
    }
}
