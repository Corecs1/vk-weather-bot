package com.bot.weather.vk.global.config;

import com.bot.weather.vk.core.dialog.Chat;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.groups.GroupsGetLongPollServerQuery;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@RequiredArgsConstructor
@Slf4j
@DependsOn("propertiesBean")
@Component(VkConfig.NAME)
public class VkConfig {
    public static final String NAME = "vk_config";
    public static VkApiClient vk;
    public static GroupActor actor;
    private static final String API_VERSION = "5.199";
    private static int ts;
    @Autowired
    private Properties properties;

    // TODO Aspect
    @PostConstruct
    private void loadConfigs() throws ClientException, ApiException {
        log.info("Starting load configs...");

        int groupId = Integer.parseInt(properties.getProperty("group_id"));
        String pictureAccessToken = properties.getProperty("picture_access_token");

        vk = new VkApiClient(HttpTransportClient.getInstance());
        actor = new GroupActor(groupId, pictureAccessToken);

        GroupsGetLongPollServerQuery longPollServer = vk.groups().getLongPollServer(actor, groupId);

        vk.groups().setLongPollSettings(actor, groupId)
                .apiVersion(API_VERSION)
                .enabled(true)
                .messageNew(true)
                .photoNew(true)
                .execute();
        vk.groupsLongPoll().getLongPollServer(actor, groupId).execute();
        ts = vk.messages().getLongPollServer(actor).execute().getTs();

        log.info("Configs is load successfully...");

        new Chat(vk, actor, 25).run();
    }

    public static VkApiClient getVk() {
        return vk;
    }

    public static GroupActor getActor() {
        return actor;
    }
}