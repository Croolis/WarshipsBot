package ru.chatbot.warship.bot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.chatbot.warship.handler.Handler;

/**
 * Created by givorenon on 30.01.17.
 */
public class WarshipBot extends TelegramLongPollingBot {

    @Autowired
    private List<Handler> handlers;

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            try {
                for (Handler handler : handlers) {
                    SendMessage msg = handler.handle(update);
                    if(msg != null) {
                        this.sendMessage(msg);
                        break;
                    }
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public String getBotUsername() {
        return "WarshipBot";
    }

    public String getBotToken() {
        return "318414106:AAHlobbBnD7YpUcEEliTpYwu2VJWyHfQ67A";
    }
}
