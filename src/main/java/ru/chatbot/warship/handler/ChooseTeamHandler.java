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

    private List<Team> teams = Arrays.asList(Team.values());

    private boolean matchCommand(Update update) {
        return playerService.getPlayer(update.getMessage().getFrom().getId()) == null;
    }

    public SendMessage handle(Update update) {
        if(!this.matchCommand(update)) {
            return null;
        } else {
            Integer userID = update.getMessage().getFrom().getId();
            String nickname = update.getMessage().getFrom().getUserName();

            try {
                Team team = Team.valueOf(update.getMessage().getText());
                playerService.createPlayer(userID, nickname, team);
                return Message.makeMessage(update.getMessage().getChatId(), Message.getJoinTeamMessage(team),
                        Keyboard.getKeyboard(Arrays.asList("INFO")));
            } catch (IllegalArgumentException e) {
                return Message.makeMessage(update.getMessage().getChatId(), Message.getSelectTeamMessage(teams),
                        Keyboard.getKeyboard(teams.stream().map(Enum::toString).collect(Collectors.toList())));
            }
        }
    }
}