package com.bot.weather.vk.core.weather.parser;

import com.bot.weather.vk.core.weather.dto.WeatherInfoDto;
import com.bot.weather.vk.core.weather.mapper.WeatherInfoMapper;
import com.bot.weather.vk.core.weather.model.WeatherInfo;
import com.bot.weather.vk.core.weather.svgPictures.WeatherPictureConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherInfoMapper weatherInfoMapper;

    private final WeatherPictureConverter weatherPictureConverter;

    @Value("${url_format}")
    private String urlFormat;

    @Value("${base_url}")
    private String baseUrl;

    @Value("${api_key}")
    private String apiKey;

    @Value("${units}")
    private String units;

    @Value("${lang}")
    private String lang;

    @Autowired
    public WeatherServiceImpl(WeatherInfoMapper weatherInfoMapper, WeatherPictureConverter weatherPictureConverter) {
        this.weatherInfoMapper = weatherInfoMapper;
        this.weatherPictureConverter = weatherPictureConverter;
    }

    @Override
    public void createWeatherPicture(String city) throws RuntimeException {
        try {
            WeatherInfo weatherInfo = getWeatherInfo(city.replace(' ', '+'));
            WeatherInfoDto weatherInfoDto = weatherInfoMapper.toDto(weatherInfo);
            weatherPictureConverter.createPngPicture(weatherInfoDto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Incorrect city name");
            throw new RuntimeException("К сожалению не удалось получить информацию.\n"
                    + "Проверьте корректность введенного города");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WeatherInfo getWeatherInfo(String city) {
        String url = String.format(urlFormat, baseUrl, city, apiKey, lang, units);
        return new RestTemplate()
                .getForEntity(url, WeatherInfo.class)
                .getBody();
    }
}