package com.example.SP.repository;

import com.example.SP.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByMonth(int month);
    List<WeatherData> findByYearAndMonthAndDay(int year, int month, int day);

}
