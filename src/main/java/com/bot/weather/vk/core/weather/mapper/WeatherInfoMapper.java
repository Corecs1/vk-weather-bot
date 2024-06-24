package com.bot.weather.vk.core.weather.mapper;

import com.bot.weather.vk.core.weather.dto.WeatherInfoDto;
import com.bot.weather.vk.core.weather.model.WeatherInfo;
import org.springframework.stereotype.Component;

@Component
public class WeatherInfoMapper {

    private static final double CONVERSION_COEFFICIENT = 0.75;

    public WeatherInfoDto toDto(WeatherInfo weatherInfo) {
        return new WeatherInfoDto(
                weatherInfo.getMain().getTemp() + "°C",
                weatherInfo.getName(),
                weatherInfo.getSys().getCountry(),
                weatherInfo.getWeather().get(0).getDescription(),
                weatherInfo.getWind().getSpeed() + " м/с.",
                weatherInfo.getMain().getPressure() * CONVERSION_COEFFICIENT + " мм рт.ст.",
                weatherInfo.getMain().getHumidity() + "%.",
                weatherInfo.getWeather().get(0).getIcon()
        );
    }
}