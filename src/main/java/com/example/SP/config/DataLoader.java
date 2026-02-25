package com.example.SP.config;

import com.opencsv.CSVReader;
import com.example.SP.model.WeatherData;
import com.example.SP.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final WeatherRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) return;

        CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("testset.csv").getInputStream()));

        String[] line;
        reader.readNext(); // Skip header

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");

        while ((line = reader.readNext()) != null) {
            try {
                WeatherData data = new WeatherData();

                // 1. Handle Date
                LocalDateTime dt = LocalDateTime.parse(line[0], formatter);
                data.setDateTime(dt);

                // Extract Year, Month, Day from the LocalDateTime object itself
                data.setYear(dt.getYear());
                data.setMonth(dt.getMonthValue());
                data.setDay(dt.getDayOfMonth());

                // 2. Map based on your CSV Header
                data.setWeatherCondition(line[1]);           // _conds
                data.setHeatIndex(parseSafe(line[5]));       // _heatindexm
                data.setHumidity(parseSafe(line[6]));        // _hum
                data.setPressure(parseSafe(line[8]));        // _pressurem
                data.setTemperature(parseSafe(line[11]));    // _tempm

                repository.save(data);

            } catch (Exception e) {
                // This prevents one bad row from stopping the whole import
                System.err.println("Error parsing row: " + String.join(",", line));
            }
        }

        reader.close();
        System.out.println("CSV Data Loaded Successfully!");
    }

    // Helper method to handle empty CSV cells or "null" strings
    private double parseSafe(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            return 0.0;
        }
        return Double.parseDouble(value.trim());
    }
}