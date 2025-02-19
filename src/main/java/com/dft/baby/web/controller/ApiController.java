package com.dft.baby.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ApiController {

    @GetMapping()
    public String hello() {
        return "Hello World!";
    }
}
