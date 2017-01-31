package ru.chatbot.warship.bot;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.chatbot.warship.handler.ChooseTeamHandler;
import ru.chatbot.warship.handler.Handler;
import ru.chatbot.warship.handler.PlayerInfoHandler;
import ru.chatbot.warship.service.PlayerService;
import ru.chatbot.warship.service.ShipService;

/**
 * Created by givorenon on 30.01.17.
 */
@Configurable
public class WarshipBot extends TelegramLongPollingBot {

    private List<Handler> handlers;
    @Autowired
    PlayerInfoHandler playerInfoHandler;
    @Autowired
    ChooseTeamHandler chooseTeamHandler;

    public void setPlayerInfoHandler(PlayerInfoHandler playerInfoHandler) {
        this.playerInfoHandler = playerInfoHandler;
    }

    public void setChooseTeamHandler(ChooseTeamHandler chooseTeamHandler) {
        this.chooseTeamHandler = chooseTeamHandler;
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public WarshipBot() {
        this.handlers = Arrays.asList(new Handler[]{chooseTeamHandler, playerInfoHandler});
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
