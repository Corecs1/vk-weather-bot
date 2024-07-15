package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.commands.keyboards.Menu;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ButtonsResponseMessage extends AbstractResponseMessage {

    private final Menu menu;

    @Autowired
    public ButtonsResponseMessage(VkApiClient vkApiClient, GroupActor groupActor, Menu menu) {
        super(vkApiClient, groupActor);
        this.menu = menu;
    }

    @Override
    public void sendMessage(Message incomeMessage) throws ClientException, ApiException {
        super.sendMessage(incomeMessage);
        sendMenu();
    }

    @Override
    public MessageType getType() {
        return MessageType.BUTTONS;
    }

    private void sendMenu() throws ApiException, ClientException {
        Random random = new Random();
        vkApiClient.messages()
                .send(groupActor)
                .message("Держи кнопки")
                .userId(getMessage().getFromId())
                .randomId(random.nextInt(10000))
                .keyboard(menu.getKeyboard())
                .execute();
    }
}