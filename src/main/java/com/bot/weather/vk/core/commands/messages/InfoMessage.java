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
public class InfoMessage extends ResponseMessage {

    @Autowired
    public InfoMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        super.sendMessage();
        sendMessagePattern(getInfoMessage());
    }

    private String getInfoMessage() {
        return """
                Набери сообщение по типу:
                Погода 'Интересующий вас город
                Например: Погода Москва
                """;
    }
}
