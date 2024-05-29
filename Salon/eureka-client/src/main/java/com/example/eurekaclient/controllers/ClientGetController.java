package com.example.eurekaclient.controllers;

import com.example.eurekaclient.dto.ClientAndRecordsDto;
import com.example.eurekaclient.entity.DateHairdresserWorkEntity;
import com.example.eurekaclient.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientGetController {
    private final ClientService clientService;

    @Autowired
    public ClientGetController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/main")
    public String mainTest() {

        return "profileClient";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/profile-client")
    public String getProfileClient(Model model) {
        ClientAndRecordsDto clientAndRecordsDto = clientService.getProfileClient();
        model.addAttribute("profileHairdresser", clientAndRecordsDto);

        return "profileClient";
    }

    @GetMapping("/addSchedule")
    public String showAddSchedule(Model model) {
        List<DateHairdresserWorkEntity> work = clientService.showAddSchedule();

        model.addAttribute("dateHairdresserWorkEntities", work);
        return "add-schedule";
    }

}
