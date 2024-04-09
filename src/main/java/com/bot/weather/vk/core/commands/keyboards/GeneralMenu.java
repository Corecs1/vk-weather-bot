package com.bot.weather.vk.core.commands.keyboards;

import com.vk.api.sdk.objects.messages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralMenu implements Menu {

    private final Keyboard keyboard = new Keyboard();

    @Override
    public Keyboard getKeyboard() {
        createMenu();
        return keyboard;
    }

    private void createMenu() {
        List<List<KeyboardButton>> allKey = new ArrayList<>();

        KeyboardButton helloButton = createButton(
                "Привет",
                TemplateActionTypeNames.TEXT,
                KeyboardButtonColor.POSITIVE
        );
        KeyboardButton myCityWeatherButton = createButton(
                "Погода в моём городе",
                TemplateActionTypeNames.TEXT,
                KeyboardButtonColor.POSITIVE
        );
        KeyboardButton anotherCityButton = createButton(
                "Погода в другом городе",
                TemplateActionTypeNames.TEXT,
                KeyboardButtonColor.POSITIVE
        );

        allKey.add(createLine(helloButton, myCityWeatherButton));
        allKey.add(createLine(anotherCityButton));

        keyboard.setButtons(allKey);
    }

    private KeyboardButton createButton(String label, TemplateActionTypeNames type, KeyboardButtonColor color) {
        return new KeyboardButton()
                .setAction(new KeyboardButtonAction().setLabel(label).setType(type))
                .setColor(color);
    }

    private List<KeyboardButton> createLine(KeyboardButton... buttons) {
        return Arrays.asList(buttons);
    }
}
