package com.example.eurekaclient.controllers;

import com.example.eurekaclient.service.ClientService;
import com.example.eurekaclient.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/client")
public class ClientPostController {
    @Value("${server.port-api-gateway}")
    private String serverPortGateWay;
    private final ScheduleService scheduleService;
    private final ClientService clientService;

    @Autowired
    public ClientPostController(ScheduleService scheduleService, ClientService clientService) {
        this.scheduleService = scheduleService;
        this.clientService = clientService;
    }

    @PostMapping("/record/{id}/remove")
    public String recordDelete(@PathVariable(value = "id") Long id) throws IOException {
        clientService.recordDelete(id);

        return String.format("redirect:http://localhost:%s/client/profile-client", serverPortGateWay);
    }

    @PostMapping("/addRecords")
    public String addRecords(@RequestBody Map<String, String> request) throws IOException {
        scheduleService.addRecords(request);

        return String.format("redirect:http://localhost:%s/client/profile-client", serverPortGateWay);
    }

}
