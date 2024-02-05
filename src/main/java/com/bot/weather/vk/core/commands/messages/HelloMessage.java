package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

public class HelloMessage extends ResponseMessage {

    public HelloMessage(Message message) throws ClientException, ApiException {
        super(message);
    }

    @Override
    public void sendMessage() {
        try {
            sendMessagePattern("Привет, " + getUserInfo().get(0).getFirstName());
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
    }
}
