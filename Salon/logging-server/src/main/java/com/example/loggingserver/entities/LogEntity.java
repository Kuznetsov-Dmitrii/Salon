package com.example.loggingserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "log")
@IdClass(LogEntityId.class)
public class LogEntity {

    @Id
    @Column(name = "received_datetime")
    private ZonedDateTime receivedDatetime;

    @Id
    @Column(name = "log_level")
    private LogLevel logLevel;

    @Id
    @Column(name = "ip_address")
    private String ipAddress;

    @Id
    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "message")
    private String message;
}
