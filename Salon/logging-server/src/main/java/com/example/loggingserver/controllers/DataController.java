package com.example.loggingserver.controllers;

import com.example.loggingserver.entities.LogDto;
import com.example.loggingserver.repositories.LogEntityRepository;
import com.example.loggingserver.utils.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DataController {
    private final EncryptionUtils encryptionUtils;
    private final LogEntityRepository logEntityRepository;

    public DataController(EncryptionUtils encryptionUtils, LogEntityRepository logEntityRepository) {
        this.encryptionUtils = encryptionUtils;
        this.logEntityRepository = logEntityRepository;
    }

    @PostMapping("/api/data")
    public void receiveData(@RequestBody LogDto logEntity) {
        if (encryptionUtils.checkCode(logEntity.getCode())) {
            System.out.println(logEntity.getLogEntity().toString());
            try {
                logEntityRepository.save(logEntity.getLogEntity());
            } catch (Exception ex) {
                log.error(String.format("Failed to save log %s " + ex.getMessage(), logEntity.getLogEntity()));
            }
        }
    }
}
