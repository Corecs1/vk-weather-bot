package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.core.commands.keyboards.GeneralMenu;
import com.bot.weather.vk.core.commands.keyboards.Menu;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.Random;

public class ButtonsMessage extends ResponseMessage {

    public ButtonsMessage(Message message) throws ClientException, ApiException {
        super(message);
    }

    @Override
    public void sendMessage() throws ClientException, ApiException {
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
