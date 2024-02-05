package com.bot.weather.vk.global.config.messagesBundle;

public interface MessageBundle {

    String NAME = "vk_MessageBundle";

    /**
     * Get localized message.
     *
     * @param key message key
     * @return localized message
     */
    String getMessage(String key);
}
