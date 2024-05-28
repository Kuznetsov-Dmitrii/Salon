package com.example.eurekaclient.utils;

import com.example.eurekaclient.entity.DateHairdresserWorkEntity;
import com.example.eurekaclient.repository.UserEntityRepo;
import com.example.eurekaclient.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkWeekUtil {

    private final UserInfoService userInfoService;
    private final UserEntityRepo userEntityRepo;

    @Value("${keycloak.realm_id}")
    private String realm_id;

    public WorkWeekUtil(UserInfoService userInfoService, UserEntityRepo userEntityRepo) {
        this.userInfoService = userInfoService;
        this.userEntityRepo = userEntityRepo;
    }

    //Создает рабочую неделю
    public DateHairdresserWorkEntity createWorkWeek(LocalDate firstDayOfWeek) {
        DateHairdresserWorkEntity workEntity = new DateHairdresserWorkEntity();

        DateHairdresserWorkEntity.DateHairdresserWorkId id = new DateHairdresserWorkEntity.DateHairdresserWorkId(
                userInfoService.getUserId(),
                firstDayOfWeek
        );
        workEntity.setId(id);
        workEntity.setHairdresser(userEntityRepo.getUserById(userInfoService.getUserId(),realm_id));

        workEntity.setMondayList(createWorkDay(firstDayOfWeek));
        workEntity.setTuesdayList(createWorkDay(firstDayOfWeek.plusDays(1)));
        workEntity.setWednesdayList(createWorkDay(firstDayOfWeek.plusDays(2)));
        workEntity.setThursdayList(createWorkDay(firstDayOfWeek.plusDays(3)));
        workEntity.setFridayList(createWorkDay(firstDayOfWeek.plusDays(4)));

        return workEntity;
    }


    private static List<LocalDateTime> createWorkDay(LocalDate day) {
        List<LocalDateTime> workHours = new ArrayList<>();
        for (int hour = 10; hour <= 17; hour++) {
            workHours.add(day.atTime(hour, 0));
        }

        return workHours;
    }


    public LocalDate getMondayOfThisWeek() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static LocalDate getMondayOfWeek(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

}
