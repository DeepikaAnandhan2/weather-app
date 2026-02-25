package com.example.SP.controller;


import com.example.SP.model.WeatherData;
import com.example.SP.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping("/month/{month}")
    public List<WeatherData> getByMonth(@PathVariable int month) {
        return service.getByMonth(month);
    }

    @GetMapping("/date/{date}")
    public List<WeatherData> getByDate(@PathVariable String date) {
        return service.getByDate(LocalDate.parse(date));
    }
}