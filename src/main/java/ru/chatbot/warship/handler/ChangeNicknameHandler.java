package ru.chatbot.warship.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.resources.Keyboard;
import ru.chatbot.warship.resources.Message;
import ru.chatbot.warship.service.PlayerService;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by givorenon on 09.02.17.
 */
public class ChangeNicknameHandler implements Handler {
    private Pattern nicknamePattern = Pattern.compile("\\/nickname .*");

    @Autowired
    PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean matchCommand(Update update) {
        return nicknamePattern.matcher(update.getMessage().getText()).matches();
    }

    @Override
    public SendMessage handle(Update update) {
        String nickname = update.getMessage().getText().substring(10);
        Integer playerId = update.getMessage().getFrom().getId();
        playerService.setNickname(playerId, nickname);
        return Message.makeReplyMessage(update, Message.getChangeNicknameMessage(nickname),
                Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
    }
}
