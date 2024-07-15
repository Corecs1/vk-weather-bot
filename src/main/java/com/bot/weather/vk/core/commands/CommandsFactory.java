package com.bot.weather.vk.core.commands;

import com.bot.weather.vk.core.commands.messages.MessageType;
import com.bot.weather.vk.core.commands.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class CommandsFactory {

    private final Map<MessageType, ResponseMessage> messageMap;

    @Autowired
    public CommandsFactory(List<ResponseMessage> messages) {
        messageMap = messages.stream()
                .collect(toMap(ResponseMessage::getType, Function.identity()));
    }

    public ResponseMessage getMessage(MessageType type) {
        return messageMap.get(type);
    }
}