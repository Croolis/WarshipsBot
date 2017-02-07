package ru.chatbot.warship.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Team;
import ru.chatbot.warship.resources.Keyboard;
import ru.chatbot.warship.resources.Message;
import ru.chatbot.warship.service.PlayerService;

/**
 * Created by givorenon on 30.01.17.
 */

public class ChooseTeamHandler implements Handler {
    @Autowired
    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean matchCommand(Update update) {
        return playerService.getPlayer(update.getMessage().getFrom().getId()) == null;
    }

    @Override
    public SendMessage handle(Update update) {
        Integer userID = update.getMessage().getFrom().getId();
        String nickname = update.getMessage().getFrom().getUserName();
        String message = update.getMessage().getText();
        List<String> teamNames = Arrays.stream(Team.values())
                .map(Team::toString)
                .collect(Collectors.toList());
        if (teamNames.contains(message)) {
            Team team = Team.valueOf(message);
            playerService.createPlayer(userID, update.getMessage().getChatId(), nickname, team);
            return Message.makeReplyMessage(update, Message.getJoinTeamMessage(team),
                    Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
        } else {
            return Message.makeReplyMessage(update, Message.getSelectTeamMessage(Arrays.asList(Team.values())),
                    Keyboard.getKeyboard(teamNames));
        }
    }
}
