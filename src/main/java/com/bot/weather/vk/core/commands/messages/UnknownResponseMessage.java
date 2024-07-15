package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnknownResponseMessage extends AbstractResponseMessage {

    @Autowired
    public UnknownResponseMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage(Message incomeMessage) throws ClientException, ApiException {
        super.sendMessage(incomeMessage);
        sendMessagePattern(getUnknownMessage());
    }

    @Override
    public MessageType getType() {
        return MessageType.UNKNOWN;
    }

    private String getUnknownMessage() {
        return """
                Я не понимаю тебя:(
                Попробуй введи слово 'Кнопки' и воспользуйся их функционалом
                """;
    }
}