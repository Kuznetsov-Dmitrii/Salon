package com.example.loggingserver.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEntityId implements Serializable {

    private ZonedDateTime receivedDatetime;

    private LogLevel logLevel;

    private String ipAddress;

    private String serviceName;
}

