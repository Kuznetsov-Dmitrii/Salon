package com.example.eurekahairdresser.controllers;

import com.example.eurekahairdresser.dto.HairdresserDto;
import com.example.eurekahairdresser.dto.RecordsHairdresserDto;
import com.example.eurekahairdresser.entity.Records;
import com.example.eurekahairdresser.repository.RecordsRepo;
import com.example.eurekahairdresser.repository.UserEntityRepo;
import com.example.eurekahairdresser.service.UserInfoService;
import com.example.eurekahairdresser.utils.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hairdresser")
public class HairdresserGetController {
    private final UserEntityRepo userEntityRepo;
    @Value("${keycloak.realm_id}")
    private String realm_id;
    private final UserInfoService userInfoService;
    private final LogUtil log;
    private final RecordsRepo recordsRepo;

    public HairdresserGetController(UserEntityRepo userEntityRepo, UserInfoService userInfoService, LogUtil log, RecordsRepo recordsRepo) {
        this.userEntityRepo = userEntityRepo;
        this.userInfoService = userInfoService;
        this.log = log;
        this.recordsRepo = recordsRepo;
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/profile-hairdresser")
    public String getProfileHairdresser(Model model) {

        HairdresserDto hairdresserDto = null;
        try {
            hairdresserDto = userEntityRepo.getHairdresserProfile(realm_id,userInfoService.getUserId());
            log.info(String.format("Парикмахер получил HairdresserDto с userId= %s",userInfoService.getUserId()));
        } catch (Exception e) {
            log.error(String.format("Ошибка получения HairdresserDto с userId= %s. %s",userInfoService.getUserId(),e.getMessage()));
        }

        model.addAttribute("profileClient", hairdresserDto);

        return "profileHairdresser";
    }

    @GetMapping("/myRecords")
    public String getRecords(Model model) {
        List<Records> recordsList=null;

        try {
            recordsList = recordsRepo.getRecordsByHairdresser(userInfoService.getUserId(), LocalDateTime.now());
            log.info("Парикмахер получил List<Records>");
        } catch (Exception e) {
            log.error(String.format("Ошибка получения List<Records>. %s",e.getMessage()));
        }

        List<RecordsHairdresserDto> recordsHairdresserDtoList = new ArrayList<>();
        if (recordsList!=null) {
            String phoneNumber="";
            for (Records record : recordsList) {
                RecordsHairdresserDto recordsHairdresserDto = RecordsHairdresserDto.builder()
                        .id(record.getId())
                        .firstName(record.getClient().getFirst_name())
                        .lastName(record.getClient().getLast_name())
                        .phoneNumber(userEntityRepo.getPhoneNumberByUserId(record.getClient().getId()))
                        .localDateTime(record.getLocalDateTime())
                        .build();

                recordsHairdresserDtoList.add(recordsHairdresserDto);
            }
        }
        log.info("Парикмахер получил список своих Records.");
        model.addAttribute("recordsHairdresser",recordsHairdresserDtoList);

        return "recordsHairdresser";
    }






}
