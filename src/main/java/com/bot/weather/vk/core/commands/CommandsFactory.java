package com.bot.weather.vk.core.commands;

import com.bot.weather.vk.core.commands.messages.*;
import com.vk.api.sdk.objects.messages.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.bot.weather.vk.core.commands.messages.MessageType.*;

@Component
public class CommandsFactory {

    private final Supplier<List<ResponseMessage>> responseMessagesSupplier;

    private final Map<MessageType, Class<? extends ResponseMessage>> TYPES_CLASS_MAP = new HashMap<>();

    @Autowired
    public CommandsFactory(Supplier<List<ResponseMessage>> responseMessagesSupplier) {
        this.responseMessagesSupplier = responseMessagesSupplier;
    }

    @PostConstruct
    private void initTypesClassMap() {
        TYPES_CLASS_MAP.put(HELLO, HelloMessage.class);
        TYPES_CLASS_MAP.put(BUTTONS, ButtonsMessage.class);
        TYPES_CLASS_MAP.put(CITY_WEATHER, CityWeatherMessage.class);
        TYPES_CLASS_MAP.put(USER_CITY_WEATHER, UserCityWeatherMessage.class);
        TYPES_CLASS_MAP.put(UNKNOWN, UnknownMessage.class);
        TYPES_CLASS_MAP.put(INFO, InfoMessage.class);
    }

    public ResponseMessage getMessage(Message message, MessageType type) {
        return responseMessagesSupplier.get()
                .stream()
                .filter(rm -> rm.getClass() == TYPES_CLASS_MAP.get(type))
                .findFirst()
                .map(responseMessage -> {
                    responseMessage.setMessage(message);
                    return responseMessage;
                }).orElse(null);
    }
}
