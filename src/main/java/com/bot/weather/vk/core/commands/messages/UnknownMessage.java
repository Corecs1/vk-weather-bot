package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

public class UnknownMessage extends ResponseMessage {

    public UnknownMessage(Message message) throws ClientException, ApiException {
        super(message);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        sendMessagePattern("Я не понимаю тебя:(" + "\n" + "Попробуй введи слово 'Кнопки' и воспользуйся их функционалом");
    }
}
