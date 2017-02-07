package ru.chatbot.warship;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean.xml", "schedule.xml");

        try {
            WarshipBot warshipBot = (WarshipBot) context.getBean("warshipBot");
            botsApi.registerBot(warshipBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}