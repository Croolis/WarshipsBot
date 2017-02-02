package ru.chatbot.warship.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

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
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("YOU ATTACK");
        } catch (IllegalArgumentException e) {
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("Sorry but there is nothing we can do");
        }
    }
}
