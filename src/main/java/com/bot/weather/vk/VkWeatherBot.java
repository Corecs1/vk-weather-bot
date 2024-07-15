package com.bot.weather.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VkWeatherBot {

    @Value("${picture_access_token}")
    private String pictureAccessToken;

    @Value("#{new Integer('${group_id}')}")
    private Integer groupId;

    @Value("${api_version}")
    private String API_VERSION;

    public static void main(String[] args) {
        SpringApplication.run(VkWeatherBot.class, args);
    }

    @Bean
    public GroupActor groupActor() {
        return new GroupActor(groupId, pictureAccessToken);
    }

    @Bean
    @SneakyThrows
    public VkApiClient vkApiClient() {
        VkApiClient vkApiClient = new VkApiClient(HttpTransportClient.getInstance());

        vkApiClient.groups().setLongPollSettings(groupActor(), groupActor().getGroupId())
                .apiVersion(API_VERSION)
                .enabled(true)
                .messageNew(true)
                .photoNew(true)
                .execute();

        vkApiClient.groupsLongPoll()
                .getLongPollServer(groupActor(), groupActor().getGroupId())
                .execute();

        return vkApiClient;
    }
}