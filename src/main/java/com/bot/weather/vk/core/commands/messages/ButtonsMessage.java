package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.commands.keyboards.GeneralMenu;
import com.bot.weather.vk.core.commands.keyboards.Menu;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope("prototype")
public class ButtonsMessage extends ResponseMessage {

    @Autowired
    public ButtonsMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        super(vkApiClient, groupActor);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
        super.sendMessage();
        sendMenu();
    }

    private void sendMenu() throws ApiException, ClientException {
        Menu menu = new GeneralMenu();
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
