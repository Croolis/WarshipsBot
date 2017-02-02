package ru.chatbot.warship.handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by givorenon on 30.01.17.
 */

public interface Handler {
    boolean matchCommand(Update update);

    SendMessage handle(Update update);
}