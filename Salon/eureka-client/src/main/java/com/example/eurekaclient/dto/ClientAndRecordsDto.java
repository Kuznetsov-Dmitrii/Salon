package com.example.eurekaclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAndRecordsDto {

    private ClientDto clientDto;

    private List<RecordsClientDto> recordsClientDtoList;

}
