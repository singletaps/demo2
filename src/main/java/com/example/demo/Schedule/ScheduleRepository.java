package com.example.demo.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 可以在这里定义自定义查询方法，比如根据状态查找
    List<Schedule> findByType(ScheduleStatus statue);
}
