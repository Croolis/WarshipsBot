package ru.chatbot.warship.handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Player;

import java.util.List;

/**
 * Created by givorenon on 30.01.17.
 */

public interface Handler {
    SendMessage handle(Update update);

    List<String> getPossibilities(Update update);
}