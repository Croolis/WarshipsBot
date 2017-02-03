package ru.chatbot.warship.handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.resources.Message;

/**
 * Created by givorenon on 02.02.17.
 */
public class DefaultHandler implements Handler {

    @Override
    public SendMessage handle(Update update) {

        try {
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText(Message.CREDITS);
        } catch (IllegalArgumentException e) {
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText(Message.SORRY_MESSAGE);
        }

    }
}
