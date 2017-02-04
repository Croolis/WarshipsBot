package ru.chatbot.warship.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.resources.Message;

import java.util.regex.Pattern;

/**
 * Created by givorenon on 03.02.17.
 */
public class AttackHandler implements Handler {
    private Pattern attackPattern = Pattern.compile("\\/attack_(\\d)+");

    @Override
    public boolean matchCommand(Update update) {
        return attackPattern.matcher(update.getMessage().getText()).matches();
    }

    @Override
    public SendMessage handle(Update update) {

        try {
            return Message.makeReplyMessage(update, Message.getAttackMessage());
        } catch (IllegalArgumentException e) {
            return Message.makeReplyMessage(update, Message.getSorryMessage());
        }
    }
}
