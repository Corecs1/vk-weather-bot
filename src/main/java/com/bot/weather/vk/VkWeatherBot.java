package com.bot.weather.vk;

import com.bot.weather.vk.global.config.VkConfig;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(VkConfig.class)
public class VkWeatherBot {

    @Value("${picture_access_token}")
    private String pictureAccessToken;

    @Value("#{new Integer('${group_id}')}")
    private Integer groupId;

    public static void main(String[] args) {
        SpringApplication.run(VkWeatherBot.class, args);
    }

    @Bean
    public GroupActor groupActor() {
        return new GroupActor(groupId, pictureAccessToken);
    }

    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }
}
