package com.tcna.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/index")
public class HomeController {

    @GetMapping
    public String home() {
        return "index";
    }

}
