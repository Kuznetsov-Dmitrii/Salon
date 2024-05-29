package com.example.eurekaclient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.logging.LogLevel;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class LogEntity implements Serializable {

    private ZonedDateTime receivedDatetime;

    private LogLevel logLevel;

    private String ipAddress;

    private String serviceName;

    private String userName;

    private String message;

}
