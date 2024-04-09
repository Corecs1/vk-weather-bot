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
        return switch (type) {
            case HELLO -> new HelloMessage(message);
            case BUTTONS -> new ButtonsMessage(message);
            case CITY_WEATHER -> new CityWeatherMessage(message);
            case USER_CITY_WEATHER -> new UserCityWeatherMessage(message);
            case UNKNOWN -> new UnknownMessage(message);
            case INFO -> new InfoMessage(message);
        };
    }
}
