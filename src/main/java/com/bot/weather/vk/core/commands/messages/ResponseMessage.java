package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

public interface ResponseMessage {

    /**
     * Метод отсылает ответное сообщение.
     *
     * @param incomeMessage Входяшее сообщение.
     */
    void sendMessage(Message incomeMessage) throws ClientException, ApiException;

    /**
     * @return Возвращает тип сообщения, необходима реализация каждого наследника.
     */
    MessageType getType();
}