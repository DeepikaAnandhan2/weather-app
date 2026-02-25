package com.example.util;

import com.example.SP.model.WeatherData;
import com.example.SP.repository.WeatherRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Component
public class CsvLoader {

    @Autowired
    private WeatherRepository repository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {

            InputStream inputStream =
                    getClass().getResourceAsStream("/testset.csv");

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream));

            CSVReader csvReader = new CSVReader(reader);
            String[] line;

            csvReader.readNext(); // skip header

            while ((line = csvReader.readNext()) != null) {

                WeatherData data = new WeatherData();

                LocalDate date = LocalDate.parse(line[0]);

                data.setDateTime(date.atStartOfDay());
                data.setYear(date.getYear());
                data.setMonth(date.getMonthValue());
                data.setDay(date.getDayOfMonth());

                data.setTemperature(Double.parseDouble(line[1]));
                data.setHumidity(Double.parseDouble(line[2]));
                data.setPressure(Double.parseDouble(line[3]));
                data.setHeatIndex(Double.parseDouble(line[4]));
                data.setWeatherCondition(line[5]);

                repository.save(data);
            }

            System.out.println("Data Loaded Successfully!");
        };
    }
}