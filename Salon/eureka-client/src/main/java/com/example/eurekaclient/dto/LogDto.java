package com.example.eurekaclient.dto;

import com.example.eurekaclient.entity.LogEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class LogDto  implements Serializable {
    private String code;
    private LogEntity logEntity;
}
