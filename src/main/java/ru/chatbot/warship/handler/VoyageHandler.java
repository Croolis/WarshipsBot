package ru.chatbot.warship.handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.resources.Keyboard;
import ru.chatbot.warship.resources.Message;

import java.util.Arrays;

/**
 * Created by givorenon on 05.02.17.
 */
public class VoyageHandler implements Handler {
    @Override
    public boolean matchCommand(Update update) {
        return update.getMessage().getText().equals("VOYAGE");
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            return Message.makeReplyMessage(update, Message.getVoyageMessage(),
                    Keyboard.getKeyboard(Arrays.asList("ATTACK", "TRADE", "TRAVEL")));
        } catch (IllegalArgumentException e) {
            return Message.makeReplyMessage(update, Message.getSorryMessage());
        }

    }
}
