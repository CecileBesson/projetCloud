package com.polytech.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping(value = "/")
    public String defaultLandingPage()
    {
        return "Cloud Project | Équipe E | Production server";
    }
}
