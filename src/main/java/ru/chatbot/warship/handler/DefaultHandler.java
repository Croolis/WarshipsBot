package ru.chatbot.warship.handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.resources.Message;

/**
 * Created by givorenon on 02.02.17.
 */
public class DefaultHandler implements Handler {
    @Override
    public boolean matchCommand(Update update) {
        return true;
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            return Message.makeReplyMessage(update, Message.getCreditsMessage());
        } catch (IllegalArgumentException e) {
            return Message.makeReplyMessage(update, Message.getSorryMessage());
        }
    }
}
