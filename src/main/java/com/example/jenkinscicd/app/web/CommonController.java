package com.example.jenkinscicd.app.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/common")
@RestController
public class CommonController {

    @GetMapping
    public String common() {
        return "hi~";
    }

    @GetMapping("/jenkins/test")
    public String jenkinsTest() {
        return "this is jenkins test~";
    }
}
