package com.example.eurekaclient.service;

import com.example.eurekaclient.dto.LogDto;
import com.example.eurekaclient.entity.LogEntity;
import com.example.eurekaclient.utils.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class LoggingService {
    private final RestTemplate restTemplate;
    private final EncryptionUtils encryptionUtils;
    @Value("${url.log-server}")
    private String urlLogServer;

    public LoggingService(RestTemplate restTemplate, EncryptionUtils encryptionUtils) {
        this.restTemplate = restTemplate;
        this.encryptionUtils = encryptionUtils;
    }

    public void sendMessage(LogEntity logEntity) {

        switch (logEntity.getLogLevel()) {
            case INFO -> log.info(logEntity.toString());
            case ERROR -> log.error(logEntity.toString());
            case TRACE -> log.trace(logEntity.toString());
            case DEBUG -> log.debug(logEntity.toString());
            case WARN -> log.warn(logEntity.toString());
        }

        LogDto logDto = null;
        try {
            logDto = LogDto.builder()
                    .code(encryptionUtils.encrypt())
                    .logEntity(logEntity)
                    .build();
        } catch (UnsupportedEncodingException e) {
            log.error("Ошибка шифрования");
        }

        try {
            restTemplate.postForObject(urlLogServer, logDto, LogDto.class);
        } catch (Exception e) {
            log.error("Не удалось отправить вообщение в LogServer");
        }
    }

}
