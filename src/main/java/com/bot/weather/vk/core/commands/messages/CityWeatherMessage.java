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
public class CityWeatherMessage extends ResponseMessage {

    @Autowired
    public CityWeatherMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        super.sendMessage();
        sendWeatherMessage();
    }

    private void sendWeatherMessage() throws ClientException, ApiException {
        String userText = getMessage().getText().toLowerCase();
        String city = userText.substring(7);
        System.out.println(city);
        try {
            OpenWeatherForPicture openWeather = new OpenWeatherForPicture(city);
            openWeather.getWeather();
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
