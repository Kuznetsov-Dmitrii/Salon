package com.example.eurekahairdresser.controllers;

import com.example.eurekahairdresser.entity.DateHairdresserWorkEntity;
import com.example.eurekahairdresser.repository.DateHairdresserWorkRepo;
import com.example.eurekahairdresser.service.ScheduleService;
import com.example.eurekahairdresser.utils.LogUtil;
import com.example.eurekahairdresser.utils.WorkWeekUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;


@Controller
@RequestMapping("/hairdresser")
public class HairdresserPostController {
    @Value("${server.port-api-gateway}")
    private String serverPortGateWay;
    private final DateHairdresserWorkRepo dateHairdresserWorkRepo;
    private final WorkWeekUtil workWeekUtil;
    private final ScheduleService scheduleService;
    private final LogUtil log;

    public HairdresserPostController(DateHairdresserWorkRepo dateHairdresserWorkRepo, WorkWeekUtil workWeekUtil, ScheduleService scheduleService, LogUtil log) {
        this.dateHairdresserWorkRepo = dateHairdresserWorkRepo;
        this.workWeekUtil = workWeekUtil;
        this.scheduleService = scheduleService;
        this.log = log;
    }

    @PostMapping("/record/{id}/remove") //
    public String recordDelete(@PathVariable(value = "id") Long id) throws IOException {
        scheduleService.recordDelete(id);

        return String.format("redirect:http://localhost:%s/hairdresser/myRecords", serverPortGateWay);
    }

    @PostMapping("/hairdressersWork")   //
    public String showHairdressers(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
        DateHairdresserWorkEntity work = workWeekUtil.createWorkWeek(date);

        try {
            dateHairdresserWorkRepo.save(work);
            log.info("Парикмахер парикмахер создал DateHairdresserWorkEntity.");
        } catch (Exception e) {
            log.error(String.format("Ошибка создания DateHairdresserWorkEntity. %s",e.getMessage()));
            throw new IOException(String.format("Ошибка создания DateHairdresserWorkEntity. %s",e.getMessage()));
        }

        return String.format("redirect:http://localhost:%s/hairdresser/profile-hairdresser", serverPortGateWay);
    }


}
