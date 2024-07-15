package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloResponseMessage extends AbstractResponseMessage {

    @Autowired
    public HelloResponseMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage(Message incomeMessage) throws ClientException, ApiException {
        super.sendMessage(incomeMessage);
        sendMessagePattern("Привет, " + getUserInfo().get(0).getFirstName());
    }

    @Override
    public MessageType getType() {
        return MessageType.HELLO;
    }
}