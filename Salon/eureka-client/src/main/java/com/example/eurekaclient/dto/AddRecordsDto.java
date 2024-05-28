package com.example.eurekaclient.dto;

import com.example.eurekaclient.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRecordsDto {

    private List<UserEntity> entities;

}
