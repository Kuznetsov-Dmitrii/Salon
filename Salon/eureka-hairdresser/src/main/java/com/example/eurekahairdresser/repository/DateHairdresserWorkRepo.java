package com.example.eurekahairdresser.repository;

import com.example.eurekahairdresser.entity.DateHairdresserWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateHairdresserWorkRepo extends JpaRepository<DateHairdresserWorkEntity, DateHairdresserWorkEntity.DateHairdresserWorkId> {

}
