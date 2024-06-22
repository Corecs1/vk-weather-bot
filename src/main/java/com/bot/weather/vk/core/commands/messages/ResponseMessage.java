package com.bot.weather.vk.core.commands.messages;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.responses.GetMessagesUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.MessageUploadResponse;
import com.vk.api.sdk.objects.photos.responses.SaveMessagesPhotoResponse;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;
import java.util.Random;

@Data
public abstract class ResponseMessage {

    protected final VkApiClient vkApiClient;

    protected final GroupActor groupActor;

    private Message message;

    private List<GetResponse> userInfo;

    public ResponseMessage(VkApiClient vkApiClient, GroupActor groupActor) {
        this.vkApiClient = vkApiClient;
        this.groupActor = groupActor;
    }

    @SneakyThrows
    private void initUserInfo() {
        this.userInfo = vkApiClient.users()
                .get(groupActor)
                .userIds(String.valueOf(message.getFromId()))
                .fields(Fields.CITY)
                .execute();
    }

    public void sendMessage() throws ClientException, ApiException {
        initUserInfo();
    }

    protected void sendMessagePattern(String text) throws ClientException, ApiException {
        Random random = new Random();
        vkApiClient.messages()
                .send(groupActor)
                .message(text)
                .userId(message.getFromId())
                .randomId(random.nextInt(10000))
                .execute();
    }

    protected void sendPicturePattern(File picture) throws ClientException, ApiException {
        Random random = new Random();

        GetMessagesUploadServerResponse uploadServerResponse = vkApiClient.photos()
                .getMessagesUploadServer(groupActor)
                .execute();
        MessageUploadResponse messageUploadResponse = vkApiClient.upload()
                .photoMessage(uploadServerResponse.getUploadUrl().toString(), picture)
                .execute();

        picture.delete();

        List<SaveMessagesPhotoResponse> photoList = vkApiClient.photos()
                .saveMessagesPhoto(groupActor, messageUploadResponse.getPhoto())
                .server(messageUploadResponse.getServer())
                .hash(messageUploadResponse.getHash())
                .execute();

        SaveMessagesPhotoResponse photo = photoList.get(0);
        String attachment = "photo" + photo.getOwnerId() + "_" + photo.getId() + "_" + photo.getAccessKey();

        vkApiClient.messages()
                .send(groupActor)
                .attachment(attachment)
                .userId(getMessage().getFromId())
                .randomId(random.nextInt(1000))
                .execute();
    }
}
