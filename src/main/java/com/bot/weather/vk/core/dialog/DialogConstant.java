package com.bot.weather.vk.core.dialog;

import com.bot.weather.vk.core.commands.messages.MessageType;

import java.util.HashMap;
import java.util.Map;

import static com.bot.weather.vk.core.commands.messages.MessageType.*;

public class DialogConstant {

    public static final Map<String, MessageType> MESSAGE_TYPES_MAP = new HashMap<>();
    public static final Map<String, MessageType> REGEXP_TYPES_MAP = new HashMap<>();

    static {
        MESSAGE_TYPES_MAP.put("hello", HELLO);
        MESSAGE_TYPES_MAP.put("привет", HELLO);
        MESSAGE_TYPES_MAP.put("кнопки", BUTTONS);
        MESSAGE_TYPES_MAP.put("погода в моём городе", USER_CITY_WEATHER);
        MESSAGE_TYPES_MAP.put("погода в другом городе", INFO);
        REGEXP_TYPES_MAP.put("погода ([а-я]|[a-z])+(\\\\s|-)?([а-я]|[a-z])*(\\\\s|-)?([а-я]|[a-z])*", CITY_WEATHER);
    }
}