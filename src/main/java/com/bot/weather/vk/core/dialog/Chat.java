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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

import static com.bot.weather.vk.core.dialog.DialogConstant.MESSAGE_TYPES_MAP;
import static com.bot.weather.vk.core.dialog.DialogConstant.REGEXP_TYPES_MAP;

@Slf4j
@Component
public class Chat extends GroupLongPollApi {

    private final VkApiClient vkApiClient;

    private final GroupActor groupActor;

    private final CommandsFactory commandsFactory;

    private static final int WAIT_TIME = 25;

    @Autowired
    public Chat(VkApiClient vkApiClient, GroupActor groupActor, CommandsFactory commandsFactory) {
        super(vkApiClient, groupActor, WAIT_TIME);
        this.vkApiClient = vkApiClient;
        this.groupActor = groupActor;
        this.commandsFactory = commandsFactory;
    }

    public CommandsFactory getCommandsFactory() {
        return commandsFactory;
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
            messageObjectNew(gson.fromJson(message.getObject(), MessageObject.class));
            return "OK";
        }

        return super.parse(message);
    }

    @SneakyThrows
    public void messageObjectNew(MessageObject messageObject) {
        Message message = messageObject.getMessage();
        logging(message);
        String userText = message.getText().toLowerCase();

        MessageTypes messageType = Optional.ofNullable(MESSAGE_TYPES_MAP.get(userText))
                .orElseGet(() -> REGEXP_TYPES_MAP.entrySet()
                        .stream()
                        .filter(map -> userText.matches(map.getKey()))
                        .findFirst()
                        .map(Map.Entry::getValue)
                        .orElse(MessageTypes.UNKNOWN));

        commandsFactory.getMessage(message, messageType).sendMessage();
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
