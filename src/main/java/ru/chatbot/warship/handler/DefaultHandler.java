package ru.chatbot.warship.handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by givorenon on 02.02.17.
 */
public class DefaultHandler implements Handler {

    @Override
    public SendMessage handle(Update update) {
        String msg = "This game is written by @givorenon @ilyailya @LevOspennikov";
        try {
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText(msg);
        } catch (IllegalArgumentException e) {
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("Sorry but there is nothing we can do");
        }

    }
}
