package com.example.eurekaclient.controllers;

import com.example.eurekaclient.dto.ClientAndRecordsDto;
import com.example.eurekaclient.entity.DateHairdresserWorkEntity;
import com.example.eurekaclient.repository.DateHairdresserWorkRepo;
import com.example.eurekaclient.service.ClientService;
import com.example.eurekaclient.utils.WorkWeekUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/client")
public class ClientGetController {
    private final DateHairdresserWorkRepo hairdresserWorkRepo;
    private final WorkWeekUtil workWeekUtil;
    private final ClientService clientService;

    @Autowired
    public ClientGetController(DateHairdresserWorkRepo hairdresserWorkRepo, WorkWeekUtil workWeekUtil, ClientService clientService) {
        this.hairdresserWorkRepo = hairdresserWorkRepo;
        this.workWeekUtil = workWeekUtil;
        this.clientService = clientService;
    }

    @GetMapping("/profile-client")
    public String getProfileClient(Model model) {

        ClientAndRecordsDto clientAndRecordsDto=clientService.getProfileClient();

        model.addAttribute("profileHairdresser", clientAndRecordsDto);

        return "profileClient";
    }

    @GetMapping("/home")
    public String getHome(Model model) {

        return "home";
    }

    @GetMapping("/addSchedule")
    public String showAddSchedule(Model model) {

        List<DateHairdresserWorkEntity> work = hairdresserWorkRepo.allDateHairdresserWorkEntityByFirstDay(workWeekUtil.getMondayOfThisWeek());

        model.addAttribute("dateHairdresserWorkEntities", work);
        return "add-schedule";
    }

}
