package com.bot.weather.vk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class VkWeatherBot {

    public static void main(String[] args) {
        SpringApplication.run(VkWeatherBot.class, args);
    }

    @Primary
    @Bean("propertiesBean")
    protected Properties properties() {
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/vkconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Ошибка при загрузке файла конфигурации");
            throw new RuntimeException(e);
        }

        return properties;
    }
}
