package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UnknownMessage extends ResponseMessage {

    @Autowired
    public UnknownMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        super.sendMessage();
        sendMessagePattern(getUnknownMessage());
    }

    private String getUnknownMessage() {
        return """
                Я не понимаю тебя:(
                Попробуй введи слово 'Кнопки' и воспользуйся их функционалом
                """;
    }
}
