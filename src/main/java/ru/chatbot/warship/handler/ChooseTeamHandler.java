package ru.chatbot.warship.handler;

import java.util.Arrays;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Team;
import ru.chatbot.warship.handler.AbstractHandler;
import ru.chatbot.warship.handler.Handler;

/**
 * Created by givorenon on 30.01.17.
 */


public class ChooseTeamHandler extends AbstractHandler implements Handler {
    private static final String CREATE_PLAYER_SQL = "INSERT INTO PLAYER (ID, NICKNAME, TEAM, GOLD) values(?, ?, ?, 0)";
    private List<Team> teams = Arrays.asList(Team.values());

    public ChooseTeamHandler() {
    }

    private boolean matchCommand(Update update) {
        return this.getPlayer(update) == null;
    }

    public SendMessage handle(Update update) {
        if(!this.matchCommand(update)) {
            return null;
        } else {
            Integer userID = update.getMessage().getFrom().getId();
            String nickname = update.getMessage().getFrom().getUserName();
            this.getPlayer(update);

            try {
                Team e = Team.valueOf(update.getMessage().getText());
                this.jdbcTemplate.update(CREATE_PLAYER_SQL, new Object[]{userID, nickname, e.getTeamId()});
                return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("You successfuly joined team " + e.toString());
            } catch (IllegalArgumentException e) {
                return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("To select team write one of " + this.teams.toString());
            }
        }
    }
}