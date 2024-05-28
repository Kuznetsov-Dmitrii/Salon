package com.example.eurekaclient.service;

import com.example.eurekaclient.entity.Records;
import com.example.eurekaclient.repository.DateHairdresserWorkRepo;
import com.example.eurekaclient.repository.RecordsRepo;
import com.example.eurekaclient.repository.UserEntityRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ScheduleService {
    private final UserEntityRepo userEntityRepo;
    private final RecordsRepo recordsRepo;
    private final UserInfoService userInfoService;
    private final DateHairdresserWorkRepo hairdresserWorkRepo;

    @Value("${keycloak.realm_id}")
    private String realm_id;

    public ScheduleService(UserEntityRepo userEntityRepo, RecordsRepo recordsRepo, UserInfoService userInfoService, DateHairdresserWorkRepo hairdresserWorkRepo) {
        this.userEntityRepo = userEntityRepo;
        this.recordsRepo = recordsRepo;
        this.userInfoService = userInfoService;
        this.hairdresserWorkRepo = hairdresserWorkRepo;
    }

    @Transactional
    public void addRecords(Map<String, String> request) {
        log.warn("зашел в метод");
        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.parseInt(request.get("firstDayOfWeek").substring(0, 4)),
                Integer.parseInt(request.get("firstDayOfWeek").substring(5, 7)),
                Integer.parseInt(request.get("firstDayOfWeek").substring(8, 10)),
                Integer.parseInt(request.get("time").substring(0, 2)),
                0);
        log.warn("localdate " + localDateTime.toString());
        Optional<Records> record = recordsRepo.getRecordsByClientAndHair(
                request.get("hairdresserId"),
                userInfoService.getUserId(),
                localDateTime.plusDays(getDayFroLocalDate(request)));
        log.warn("record " + record.toString());
        List<Records> recordsList = recordsRepo.getRecordsListByClientId(userInfoService.getUserId(), LocalDateTime.now());
        log.warn("recordsList " + recordsList.toString());
        if (record.isEmpty() && recordsList.size() < 11) {
            log.error("пройдена record.isEmpty() && recordsList.size()<11");
            switch (request.get("day")) {
                case ("mondayList") -> {
                    if (localDateTime.isAfter(LocalDateTime.now())) {
                        hairdresserWorkRepo.deleteWorkMondayByHairAndTime(request.get("hairdresserId"), localDateTime);
                        checkDate(request, localDateTime);
                    }
                }
                case ("tuesdayList") -> {
                    if (localDateTime.plusDays(1).isAfter(LocalDateTime.now())) {
                        hairdresserWorkRepo.deleteWorkTuesdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(1));
                        checkDate(request, localDateTime.plusDays(1));
                    }
                }
                case ("wednesdayList") -> {
                    if (localDateTime.plusDays(1).isAfter(LocalDateTime.now())) {
                        hairdresserWorkRepo.deleteWorkWednesdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(2));
                        checkDate(request, localDateTime.plusDays(2));
                    }
                }
                case ("thursdayList") -> {
                    if (localDateTime.plusDays(1).isAfter(LocalDateTime.now())) {

                        hairdresserWorkRepo.deleteWorkThursdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(3));
                        checkDate(request, localDateTime.plusDays(3));
                    }
                }
                case ("fridayList") -> {
                    if (localDateTime.plusDays(1).isAfter(LocalDateTime.now())) {
                        hairdresserWorkRepo.deleteWorkFridayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(4));
                        checkDate(request, localDateTime.plusDays(4));
                    }
                }
            }
        } else {
            log.error("Такая запись существует");
        }
    }

    private void checkDate(Map<String, String> request, LocalDateTime localDateTime) {
        Records records = Records.builder()
                .client(userEntityRepo.getUserById(userInfoService.getUserId(), realm_id))
                .hairdresser(userEntityRepo.getUserById(request.get("hairdresserId"), realm_id))
                .localDateTime(localDateTime)
                .build();

        recordsRepo.save(records);
    }

    private static Integer getDayFroLocalDate(Map<String, String> request) {

        switch (request.get("day")) {
            case ("mondayList") -> {
                return 0;
            }
            case ("tuesdayList") -> {
                return 1;
            }
            case ("wednesdayList") -> {
                return 2;
            }
            case ("thursdayList") -> {
                return 3;
            }
            case ("fridayList") -> {
                return 4;
            }
        }
        return 0;
    }

}

