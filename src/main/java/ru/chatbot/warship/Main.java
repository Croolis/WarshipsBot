package ru.chatbot.warship;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.chatbot.warship.bot.WarshipBot;

/**
 * Created by givorenon on 30.01.17.
 */

public class Main {

    public Main() {
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new WarshipBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}