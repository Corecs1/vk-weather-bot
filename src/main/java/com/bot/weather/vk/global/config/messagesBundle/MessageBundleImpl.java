package com.bot.weather.vk.global.config.messagesBundle;

import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component(MessageBundle.NAME)
public class MessageBundleImpl implements MessageBundle {

    @Override
    public String getMessage(String key) {
        // TODO Локаль можно определять по стране пользователя (Россия, Кз, Уз, Тадж будет - Русский, остальное по дефолту англ)
        // TODO Либо сделать кнопку переключения языка
        ResourceBundle messages = ResourceBundle.getBundle("messages");
        return null;
    }
}
