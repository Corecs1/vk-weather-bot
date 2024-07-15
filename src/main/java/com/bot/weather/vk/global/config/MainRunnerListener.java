package com.bot.weather.vk.global.config;

import com.bot.weather.vk.core.dialog.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MainRunnerListener {

    private final Chat chat;

    @Autowired
    public MainRunnerListener(Chat chat) {
        this.chat = chat;
    }

    @EventListener
    public void handleContextRefreshed(ContextRefreshedEvent contextRefreshedEvent) {
        chat.run();
    }
}