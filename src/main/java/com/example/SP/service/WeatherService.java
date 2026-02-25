package com.example.SP.service;


import com.example.SP.model.WeatherData;
import com.example.SP.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository repository;

    public List<WeatherData> getByMonth(int month) {
        return repository.findByMonth(month);
    }

    public List<WeatherData> getByDate(LocalDate date) {
        return repository.findByYearAndMonthAndDay(
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth()
        );
    }

}