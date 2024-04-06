package com.bot.weather.vk.global.config.messagesBundle;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component(MessageBundle.NAME)
public class MessageBundleImpl implements MessageBundle {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.of("ru"));
    }
}
