package com.example.loggingserver.kafka;


import com.example.loggingserver.entities.LogEntity;
import com.example.loggingserver.repositories.LogEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    private final LogEntityRepository logEntityRepository;

    @Autowired
    public KafkaConsumer(LogEntityRepository logEntityRepository) {
        this.logEntityRepository = logEntityRepository;
    }

    @KafkaListener(topics = "service-log")
    public void listenServices(ConsumerRecord<Long, LogEntity> record) {
        try {
            logEntityRepository.save(record.value());
        } catch (Exception ex) {
            log.error(String.format("Failed to save log %s " + ex.getMessage(), record.value()));
        }
    }
}

