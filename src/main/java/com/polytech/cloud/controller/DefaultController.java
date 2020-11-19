package com.polytech.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class DefaultController {

    @Value("${:classpath:/static/index.html}")
    private Resource index;

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity defaultLandingPage() throws IOException {
        return ResponseEntity.ok(new InputStreamResource(index.getInputStream()));
    }
}
