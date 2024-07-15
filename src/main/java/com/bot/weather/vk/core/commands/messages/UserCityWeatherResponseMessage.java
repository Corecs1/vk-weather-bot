package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.FilePath;
import com.bot.weather.vk.core.weather.parser.WeatherService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UserCityWeatherResponseMessage extends AbstractResponseMessage {

    private final WeatherService weatherService;

    @Autowired
    public UserCityWeatherResponseMessage(VkApiClient vkApiClient, GroupActor groupActor, WeatherService weatherService) {
        super(vkApiClient, groupActor);
        this.weatherService = weatherService;
    }

    @Override
    public void sendMessage(Message incomeMessage) throws ClientException, ApiException {
        super.sendMessage(incomeMessage);

        String userCity = String.valueOf(getUserInfo().get(0).getCity());
        if (userCity.equals("null")) {
            sendMessagePattern("К сожалению информация о городе скрыта в твоём профиле");
        } else {
            weatherService.createWeatherPicture(getUserInfo().get(0).getCity().getTitle());
            File picture = new File(FilePath.weatherCascade);
            sendPicturePattern(picture);
        }
    }

    @Override
    public MessageType getType() {
        return MessageType.USER_CITY_WEATHER;
    }
}