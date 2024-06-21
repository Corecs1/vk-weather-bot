package com.bot.weather.vk.global.config;

import com.bot.weather.vk.core.dialog.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Chat chat;

    @Autowired
    public ContextRefreshedListener(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        chat.run();
    }
}