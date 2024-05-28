package com.example.eurekahairdresser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordsHairdresserDto {

    private Long id;
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDateTime localDateTime;

}
