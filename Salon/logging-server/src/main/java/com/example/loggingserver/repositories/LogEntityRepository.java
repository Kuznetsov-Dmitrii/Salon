package com.example.loggingserver.repositories;

import com.example.loggingserver.entities.LogEntity;
import com.example.loggingserver.entities.LogEntityId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LogEntityRepository extends JpaRepository<LogEntity, LogEntityId> {
}

