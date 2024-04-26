package com.example.eurekaclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping("/driver")
    public String driver(Model model) {
       // model.addAttribute("driver", driverService.Alldriver());
        return "main";
    }

}
