package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.FilePath;
import com.bot.weather.vk.core.weather.parser.WeatherService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@Scope("prototype")
public class CityWeatherMessage extends ResponseMessage {

    private final WeatherService weatherService;

    @Autowired
    public CityWeatherMessage(VkApiClient vkApiClient, GroupActor groupActor, WeatherService weatherService) {
        super(vkApiClient, groupActor);
        this.weatherService = weatherService;
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        super.sendMessage();
        sendWeatherMessage();
    }

    private void sendWeatherMessage() throws ClientException, ApiException {
        String userText = getMessage().getText().toLowerCase();
        String city = userText.substring(7);
        try {
            weatherService.createWeatherPicture(city);
            File picture = new File(FilePath.weatherCascade);
            sendPicturePattern(picture);
        } catch (RuntimeException e) {
            e.printStackTrace();
            sendMessagePattern(getDefaultWeatherMessage(city));
        }
    }

    private String getDefaultWeatherMessage(String city) {
        return """
                Для города %s информация о погоде отсутствует
                Проверьте корректность введенного города
                 """.formatted(city);
    }
}
