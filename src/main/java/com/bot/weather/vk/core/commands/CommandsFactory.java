package com.bot.weather.vk.core.commands;

import com.bot.weather.vk.core.commands.messages.*;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

public class CommandsFactory {
    private final Message message;

    public CommandsFactory(Message message) {
        this.message = message;
    }

    public ResponseMessage getMessage(MessageTypes type) throws ClientException, ApiException {
        ResponseMessage toReturn;
        switch (type) {
            case HELLO:
                toReturn = new HelloMessage(message);
                break;
            case BUTTONS:
                toReturn = new ButtonsMessage(message);
                break;
            case CITY_WEATHER:
                toReturn = new CityWeatherMessage(message);
                break;
            case USER_CITY_WEATHER:
                toReturn = new UserCityWeatherMessage(message);
                break;
            case UNKNOWN:
                toReturn = new UnknownMessage(message);
                break;
            case INFO:
                toReturn = new InfoMessage(message);
                break;
            default:
                throw new IllegalArgumentException("Wrong message type " + type);
        }
        return toReturn;
    }
}
