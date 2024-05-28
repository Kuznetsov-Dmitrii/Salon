package com.example.eurekaclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordsClientDto {

    private Long id;
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDateTime localDateTime;

}
