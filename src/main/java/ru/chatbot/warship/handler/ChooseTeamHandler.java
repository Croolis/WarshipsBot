package ru.chatbot.warship.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Player;
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

    @Override
    public List<String> getPossibilities(Update update) {
        Player player = playerService.getPlayer(update.getMessage().getFrom().getId());
        if (player == null) {
            return teams.stream().map(Enum::toString).collect(Collectors.toList());
        } else {
            return Arrays.asList("INFO");
        }

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
                return Message.makeReplyMessage(update, Message.getJoinTeamMessage(team));
            } catch (IllegalArgumentException e) {
                return Message.makeReplyMessage(update, Message.getSelectTeamMessage(teams));
            }
        }
    }
}