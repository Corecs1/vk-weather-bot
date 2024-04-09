package com.bot.weather.vk.core.dialog;

import com.bot.weather.vk.core.commands.CommandsFactory;
import com.bot.weather.vk.core.commands.messages.MessageTypes;
import com.google.gson.annotations.SerializedName;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.events.Events;
import com.vk.api.sdk.events.longpoll.GroupLongPollApi;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.messages.CallbackMessage;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Chat extends GroupLongPollApi {

    private final VkApiClient vkApiClient;

    private final GroupActor groupActor;

    public Chat(VkApiClient vkApiClient, GroupActor groupActor, int waitTime) {
        super(vkApiClient, groupActor, waitTime);

        this.vkApiClient = vkApiClient;
        this.groupActor = groupActor;
    }

    /**
     * Переопределён метод, чтобы сделать прослойку с кастосмным {@link MessageObject}.
     * Необходимо для решения <a href="https://github.com/VKCOM/vk-java-sdk/issues/246">проблемы</a> в коробке.
     */
    @Override
    protected String parse(CallbackMessage message) {
        if (Objects.isNull(message.getType()))
            return "OK";

        if (message.getType() == Events.MESSAGE_NEW) {
            try {
                messageObjectNew(message.getGroupId(), gson.fromJson(message.getObject(), MessageObject.class));
            } catch (ClientException | ApiException e) {
                throw new RuntimeException(e);
            }
            return "OK";
        }

        return super.parse(message);
    }

    public void messageObjectNew(Integer groupId, MessageObject messageObject) throws ClientException, ApiException {
        Message message = messageObject.getMessage();
        logging(message);

        CommandsFactory commandsFactory = new CommandsFactory(message);
        String userText = message.getText().toLowerCase();

        if (userText.equals("hello") || userText.equals("привет")) {
            commandsFactory.getMessage(MessageTypes.HELLO).sendMessage();
        } else if (userText.equals("кнопки")) {
            commandsFactory.getMessage(MessageTypes.BUTTONS).sendMessage();
        } else if (userText.equals("погода в моём городе")) {
            commandsFactory.getMessage(MessageTypes.USER_CITY_WEATHER).sendMessage();
        } else if (userText.equals("погода в другом городе")) {
            commandsFactory.getMessage(MessageTypes.INFO).sendMessage();
        } else if (userText.matches("погода ([а-я]|[a-z])+(\\s|-)?([а-я]|[a-z])*(\\s|-)?([а-я]|[a-z])*")) {
            commandsFactory.getMessage(MessageTypes.CITY_WEATHER).sendMessage();
        } else {
            commandsFactory.getMessage(MessageTypes.UNKNOWN).sendMessage();
        }
//        VKConfig.setTs(config.getVk().messages().getLongPollServer(config.getActor()).execute().getTs());
    }

    private void logging(Message message) {
        List<GetResponse> userInfo;

        try {
            userInfo = vkApiClient.users()
                    .get(groupActor)
                    .userIds(String.valueOf(message.getFromId()))
                    .fields(Fields.CITY)
                    .execute();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }

        log.info(String.format(
                "Message: %s Date: %s UserInfo: %s",
                message.getText(),
                Date.from(Instant.ofEpochSecond(message.getDate())),
                userInfo
        ));
    }

    private static class MessageObject {
        @SerializedName("message")
        private Message message;

        public Message getMessage() {
            return message;
        }
    }
}
