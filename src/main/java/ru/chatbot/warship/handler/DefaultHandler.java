package ru.chatbot.warship.handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.resources.Message;
import java.util.Arrays;
import java.util.List;

/**
 * Created by givorenon on 02.02.17.
 */

public class DefaultHandler implements Handler {

    @Override
    public List<String> getPossibilities(Update update) {
        return Arrays.asList("INFO");

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
