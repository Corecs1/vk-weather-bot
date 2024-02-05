package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.FilePath;
import com.bot.weather.vk.core.weather.parser.OpenWeatherForPicture;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.io.File;

public class UserCityWeatherMessage extends ResponseMessage {

    public UserCityWeatherMessage(Message message) throws ClientException, ApiException {
        super(message);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
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
