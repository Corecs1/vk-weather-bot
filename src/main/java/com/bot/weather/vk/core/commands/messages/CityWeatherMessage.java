package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.FilePath;
import com.bot.weather.vk.core.weather.parser.OpenWeatherForPicture;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.io.File;

public class CityWeatherMessage extends ResponseMessage {

    public CityWeatherMessage(Message message) throws ClientException, ApiException {
        super(message);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        String userText = getMessage().getText().toLowerCase();
        String city = userText.substring(7);
        System.out.println(city);
        String weather = "Для города " + city + " информация о погоде отсутствует\nПроверьте корректность введенного города";
        try {
            OpenWeatherForPicture openWeather = new OpenWeatherForPicture(city);
            openWeather.getWeather();
            File picture = new File(FilePath.weatherCascade);
            sendPicturePattern(picture);
        } catch (RuntimeException e) {
            e.printStackTrace();
            sendMessagePattern(weather);
        }
    }
}
