package com.bot.weather.vk.core.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherInfoDto {
    private String temperature;
    private String city;
    private String country;
    private String description;
    private String wind;
    private String pressure;
    private String humidity;
    private String weatherCondition;
}