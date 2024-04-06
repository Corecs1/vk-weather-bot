package com.bot.weather.vk.core.commands.messages;

import com.bot.weather.vk.global.config.VkConfig;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.responses.GetMessagesUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.MessageUploadResponse;
import com.vk.api.sdk.objects.photos.responses.SaveMessagesPhotoResponse;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;

import java.io.File;
import java.util.List;
import java.util.Random;

public abstract class ResponseMessage {
    private final Message message;
    private final List<GetResponse> userInfo;

    public ResponseMessage(Message message) throws ClientException, ApiException {
        this.message = message;
        this.userInfo = VkConfig.getVk().users()
                .get(VkConfig.getActor())
                .userIds(String.valueOf(message.getFromId()))
                .fields(Fields.CITY)
                .execute();
    }

    public Message getMessage() {
        return message;
    }

    public List<GetResponse> getUserInfo() {
        return userInfo;
    }

    public abstract void sendMessage() throws ClientException, ApiException;

    protected void sendMessagePattern(String text) throws ClientException, ApiException {
        Random random = new Random();
        VkConfig.getVk().messages()
                .send(VkConfig.getActor())
                .message(text)
                .userId(message.getFromId())
                .randomId(random.nextInt(10000))
                .execute();
    }

    protected void sendPicturePattern(File picture) throws ClientException, ApiException {
        Random random = new Random();

        GetMessagesUploadServerResponse uploadServerResponse = VkConfig.getVk()
                .photos()
                .getMessagesUploadServer(VkConfig.getActor())
                .execute();
        MessageUploadResponse messageUploadResponse = VkConfig.getVk()
                .upload()
                .photoMessage(uploadServerResponse.getUploadUrl().toString(), picture)
                .execute();

        picture.delete();

        List<SaveMessagesPhotoResponse> photoList = VkConfig.getVk()
                .photos()
                .saveMessagesPhoto(VkConfig.getActor(), messageUploadResponse.getPhoto())
                .server(messageUploadResponse.getServer())
                .hash(messageUploadResponse.getHash())
                .execute();

        SaveMessagesPhotoResponse photo = photoList.get(0);
        String attachment = "photo" + photo.getOwnerId() + "_" + photo.getId() + "_" + photo.getAccessKey();

        VkConfig.getVk()
                .messages()
                .send(VkConfig.getActor())
                .attachment(attachment)
                .userId(getMessage().getFromId())
                .randomId(random.nextInt(1000))
                .execute();
    }
}
