package com.example.eurekahairdresser.controllers;

import com.example.eurekahairdresser.dto.HairdresserDto;
import com.example.eurekahairdresser.dto.RecordsHairdresserDto;
import com.example.eurekahairdresser.entity.Records;
import com.example.eurekahairdresser.repository.RecordsRepo;
import com.example.eurekahairdresser.repository.UserEntityRepo;
import com.example.eurekahairdresser.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/hairdresser")
public class HairdresserGetController {
    private final UserEntityRepo userEntityRepo;

    @Value("${keycloak.realm_id}")
    private String realm_id;
    private final UserInfoService userInfoService;
    private final RecordsRepo recordsRepo;

    public HairdresserGetController(UserEntityRepo userEntityRepo, UserInfoService userInfoService, RecordsRepo recordsRepo) {
        this.userEntityRepo = userEntityRepo;
        this.userInfoService = userInfoService;
        this.recordsRepo = recordsRepo;
    }


    @GetMapping("/home")
    public String getHome(Model model) {

        return "home";
    }

    @GetMapping("/profile-hairdresser")
    public String getProfileHairdresser(Model model) {

        HairdresserDto hairdresserDto = null;
        try {
            hairdresserDto = userEntityRepo.getHairdresserProfile(realm_id,userInfoService.getUserId());
        } catch (DataAccessException e) {
            System.err.println("Ошибка доступа к базе данных: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e.getMessage());
        }

        model.addAttribute("profileClient", hairdresserDto);

        return "profileHairdresser";
    }

    @GetMapping("/myRecords")
    public String getRecords(Model model) {
        List<Records> recordsList=recordsRepo.getRecordsByHairdresser(userInfoService.getUserId(), LocalDateTime.now());

        List<RecordsHairdresserDto> recordsHairdresserDtoList=new ArrayList<>();
        for (Records record:recordsList){
            RecordsHairdresserDto recordsHairdresserDto= RecordsHairdresserDto.builder()
                    .id(record.getId())
                    .firstName(record.getClient().getFirst_name())
                    .lastName(record.getClient().getLast_name())
                    .phoneNumber(userEntityRepo.getPhoneNumberByUserId(record.getClient().getId()))
                    .localDateTime(record.getLocalDateTime())
                    .build();

            recordsHairdresserDtoList.add(recordsHairdresserDto);
        }
        model.addAttribute("recordsHairdresser",recordsHairdresserDtoList);

        return "recordsHairdresser";
    }






}
