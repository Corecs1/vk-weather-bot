package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfoResponseMessage extends AbstractResponseMessage {

    @Autowired
    public InfoResponseMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage(Message incomeMessage) throws ClientException, ApiException {
        super.sendMessage(incomeMessage);
        sendMessagePattern(getInfoMessage());
    }

    @Override
    public MessageType getType() {
        return MessageType.INFO;
    }

    private String getInfoMessage() {
        return """
                Набери сообщение по типу:
                Погода 'Интересующий вас город
                Например: Погода Москва
                """;
    }
}