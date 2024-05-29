package com.example.eurekaclient.service;

import com.example.eurekaclient.entity.Records;
import com.example.eurekaclient.repository.DateHairdresserWorkRepo;
import com.example.eurekaclient.repository.RecordsRepo;
import com.example.eurekaclient.repository.UserEntityRepo;
import com.example.eurekaclient.utils.LogUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ScheduleService {
    private final UserEntityRepo userEntityRepo;
    private final RecordsRepo recordsRepo;
    private final UserInfoService userInfoService;
    private final DateHairdresserWorkRepo hairdresserWorkRepo;
    private final LogUtil log;
    @Value("${keycloak.realm_id}")
    private String realm_id;

    public ScheduleService(UserEntityRepo userEntityRepo, RecordsRepo recordsRepo, UserInfoService userInfoService, DateHairdresserWorkRepo hairdresserWorkRepo, LogUtil log) {
        this.userEntityRepo = userEntityRepo;
        this.recordsRepo = recordsRepo;
        this.userInfoService = userInfoService;
        this.hairdresserWorkRepo = hairdresserWorkRepo;
        this.log = log;
    }

    @Transactional
    public void addRecords(Map<String, String> request) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.parseInt(request.get("firstDayOfWeek").substring(0, 4)),
                Integer.parseInt(request.get("firstDayOfWeek").substring(5, 7)),
                Integer.parseInt(request.get("firstDayOfWeek").substring(8, 10)),
                Integer.parseInt(request.get("time").substring(0, 2)),
                0);

        Optional<Records> record = recordsRepo.getRecordsByClientAndHair(
                request.get("hairdresserId"),
                userInfoService.getUserId(),
                localDateTime.plusDays(getDayFroLocalDate(request)));

        List<Records> recordsList = null;
        try {
            recordsList = recordsRepo.getRecordsListByClientId(userInfoService.getUserId(), LocalDateTime.now());
            log.info("Пользователь получил List<Records>");
        } catch (Exception e) {
            log.error(String.format("Ошибка получения  List<Records>. %s", e.getMessage()));
            throw new IOException(String.format("Ошибка получения  List<Records>. %s", e.getMessage()));
        }

        String day = request.get("day");
        if (record.isEmpty() && recordsList.size() < 5 && !request.get("hairdresserId").equals(userInfoService.getUserId())) {
            switch (day) {
                case ("mondayList") -> {
                    if (localDateTime.isAfter(LocalDateTime.now())) {
                        try {
                            hairdresserWorkRepo.deleteWorkMondayByHairAndTime(request.get("hairdresserId"), localDateTime);
                            log.info(String.format("Пользователь удалил DateHairdresserWork c work_id_hairdresser_id = %s,monday_list = %s.", request.get("hairdresserId"), localDateTime));
                        } catch (Exception e) {
                            log.error(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                            throw new IOException(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                        }
                        checkDate(request, localDateTime);
                    }
                }
                case ("tuesdayList") -> {
                    if (localDateTime.plusDays(1).isAfter(LocalDateTime.now())) {
                        try {
                            hairdresserWorkRepo.deleteWorkTuesdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(1));
                            log.info(String.format("Пользователь удалил DateHairdresserWork c work_id_hairdresser_id = %s,monday_list = %s.", request.get("hairdresserId"), localDateTime));
                        } catch (Exception e) {
                            log.error(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                            throw new IOException(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                        }
                        checkDate(request, localDateTime.plusDays(1));
                    }
                }
                case ("wednesdayList") -> {
                    if (localDateTime.plusDays(2).isAfter(LocalDateTime.now())) {
                        try {
                            hairdresserWorkRepo.deleteWorkWednesdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(2));
                            log.info(String.format("Пользователь удалил DateHairdresserWork c work_id_hairdresser_id = %s,monday_list = %s.", request.get("hairdresserId"), localDateTime));
                        } catch (Exception e) {
                            log.error(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                            throw new IOException(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                        }
                        checkDate(request, localDateTime.plusDays(2));
                    }
                }
                case ("thursdayList") -> {
                    if (localDateTime.plusDays(3).isAfter(LocalDateTime.now())) {
                        try {
                            hairdresserWorkRepo.deleteWorkThursdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(3));
                            log.info(String.format("Пользователь удалил DateHairdresserWork c work_id_hairdresser_id = %s,monday_list = %s.", request.get("hairdresserId"), localDateTime));
                        } catch (Exception e) {
                            log.error(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                            throw new IOException(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                        }
                        checkDate(request, localDateTime.plusDays(3));
                    }
                }
                case ("fridayList") -> {
                    if (localDateTime.plusDays(4).isAfter(LocalDateTime.now())) {
                        try {
                            hairdresserWorkRepo.deleteWorkFridayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(4));
                            log.info(String.format("Пользователь удалил DateHairdresserWork c work_id_hairdresser_id = %s,monday_list = %s.", request.get("hairdresserId"), localDateTime));
                        } catch (Exception e) {
                            log.error(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                            throw new IOException(String.format("Ошибка удаления DateHairdresserWork. %s", e.getMessage()));
                        }
                        checkDate(request, localDateTime.plusDays(4));
                    }
                }
            }
        } else {
            log.error("Пользователь не смог записаться на стрижку");
        }
    }

    private void checkDate(Map<String, String> request, LocalDateTime localDateTime) throws IOException {
        Records records = Records.builder()
                .client(userEntityRepo.getUserById(userInfoService.getUserId(), realm_id))
                .hairdresser(userEntityRepo.getUserById(request.get("hairdresserId"), realm_id))
                .localDateTime(localDateTime)
                .build();

        try {
            recordsRepo.save(records);
            log.info("Пользователь сохранил Records");
        } catch (Exception e) {
            log.error(String.format("Ошибка сохранения Records. %s", e.getMessage()));
            throw new IOException(String.format("Ошибка сохранения Records. %s", e.getMessage()));
        }
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

