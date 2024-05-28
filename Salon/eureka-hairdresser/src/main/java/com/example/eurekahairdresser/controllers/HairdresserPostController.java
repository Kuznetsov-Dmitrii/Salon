package com.example.eurekahairdresser.controllers;

import com.example.eurekahairdresser.entity.DateHairdresserWorkEntity;
import com.example.eurekahairdresser.repository.DateHairdresserWorkRepo;
import com.example.eurekahairdresser.service.ScheduleService;
import com.example.eurekahairdresser.utils.WorkWeekUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/hairdresser")
public class HairdresserPostController {
    @Value("${server.port-api-gateway}")
    private String serverPortGateWay;
    private final DateHairdresserWorkRepo dateHairdresserWorkRepo;
    private final WorkWeekUtil workWeekUtil;
    private final ScheduleService scheduleService;

    public HairdresserPostController(DateHairdresserWorkRepo dateHairdresserWorkRepo, WorkWeekUtil workWeekUtil, ScheduleService scheduleService) {
        this.dateHairdresserWorkRepo = dateHairdresserWorkRepo;
        this.workWeekUtil = workWeekUtil;
        this.scheduleService = scheduleService;
    }

    @PostMapping("/record/{id}/remove")
    public String recordDelete(@PathVariable(value = "id") Long id) {

        scheduleService.recordDelete(id);

        return String.format("redirect:http://localhost:%s/hairdresser/myRecords", serverPortGateWay);
    }

    @PostMapping("/hairdressersWork")
    public String showHairdressers(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        System.out.println("/hairdressersWork");
        DateHairdresserWorkEntity work = workWeekUtil.createWorkWeek(date);

        dateHairdresserWorkRepo.save(work);

        return String.format("redirect:http://localhost:%s/hairdresser/profile-hairdresser", serverPortGateWay);
    }


}
