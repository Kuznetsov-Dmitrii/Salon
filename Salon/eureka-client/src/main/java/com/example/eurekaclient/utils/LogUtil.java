package com.example.eurekaclient.utils;

import com.example.eurekaclient.entity.LogEntity;
import com.example.eurekaclient.service.LoggingService;
import com.example.eurekaclient.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class LogUtil {
    @Value("${spring.application.name}")
    private String serviceName;
    private final UserInfoService userInfoService;
    private final LoggingService loggingService;

    public LogUtil(UserInfoService userInfoService, LoggingService loggingService) {
        this.userInfoService = userInfoService;
        this.loggingService = loggingService;
    }

    public void info(String message) {

        loggingService.sendMessage(getLogEntity(message, LogLevel.INFO));
    }

    public void debug(String message) {

        loggingService.sendMessage(getLogEntity(message, LogLevel.DEBUG));
    }

    public void error(String message) {

        loggingService.sendMessage(getLogEntity(message, LogLevel.ERROR));
    }

    public void warn(String message) {

        loggingService.sendMessage(getLogEntity(message, LogLevel.WARN));
    }

    public void trace(String message) {

        loggingService.sendMessage(getLogEntity(message, LogLevel.TRACE));
    }

    private LogEntity getLogEntity(String message, LogLevel logLevel) {

        return LogEntity.builder()
                .serviceName(serviceName)
                .receivedDatetime(ZonedDateTime.now())
                .logLevel(logLevel)
                .ipAddress(userInfoService.getIpAddress())
                .userName(userInfoService.getUserName())
                .message(message)
                .build();
    }
}
