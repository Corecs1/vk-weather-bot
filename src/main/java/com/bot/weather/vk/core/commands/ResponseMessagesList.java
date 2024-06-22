package com.bot.weather.vk.core.commands;

import com.bot.weather.vk.core.commands.messages.ResponseMessage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@Scope("prototype")
public class ResponseMessagesList {

    private final List<ResponseMessage> responseMessages;

    @Autowired
    public ResponseMessagesList(List<ResponseMessage> responseMessages) {
        this.responseMessages = responseMessages;
    }
}