package com.bot.weather.vk.global.config;

import com.bot.weather.vk.core.dialog.Chat;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration(VkConfig.NAME)
public class VkConfig {

    public static final String NAME = "vk_config";

    @Value("${api_version}")
    private String API_VERSION;

    private VkApiClient vkApiClient;

    private GroupActor groupActor;

    @Autowired
    public VkConfig(VkApiClient vkApiClient, GroupActor groupActor) {
        this.vkApiClient = vkApiClient;
        this.groupActor = groupActor;
    }

    @PostConstruct
    private void runChat() throws ClientException, ApiException {
        setupVkClient();
        new Chat(vkApiClient, groupActor, 25).run();
    }

    private void setupVkClient() throws ClientException, ApiException {
        vkApiClient.groups().setLongPollSettings(groupActor, groupActor.getGroupId())
                .apiVersion(API_VERSION)
                .enabled(true)
                .messageNew(true)
                .photoNew(true)
                .execute();

        vkApiClient.groupsLongPoll()
                .getLongPollServer(groupActor, groupActor.getGroupId())
                .execute();
    }
}