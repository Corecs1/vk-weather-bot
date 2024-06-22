package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.FilePath;
import com.bot.weather.vk.core.weather.parser.OpenWeatherForPicture;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Scope("prototype")
public class UserCityWeatherMessage extends ResponseMessage {

    @Autowired
    public UserCityWeatherMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        super.sendMessage();

        String userCity = String.valueOf(getUserInfo().get(0).getCity());
        if (userCity.equals("null")) {
            sendMessagePattern("К сожалению информация о городе скрыта в твоём профиле");
        } else {
            OpenWeatherForPicture openWeather = new OpenWeatherForPicture(getUserInfo().get(0).getCity().getTitle());
            openWeather.getWeather();
            File picture = new File(FilePath.weatherCascade);
            sendPicturePattern(picture);
        }
    }
}
