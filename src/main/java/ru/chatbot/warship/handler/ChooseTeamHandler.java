package ru.chatbot.warship.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.bot.WarshipBot;
import ru.chatbot.warship.config.ServiceConfig;
import ru.chatbot.warship.entity.Team;
import ru.chatbot.warship.service.PlayerService;

/**
 * Created by givorenon on 30.01.17.
 */


public class ChooseTeamHandler implements Handler {
    private PlayerService playerService = (PlayerService) WarshipBot.context.getBean("playerService");

    private List<Team> teams = Arrays.asList(Team.values());
    private ApplicationContext context;

    public ChooseTeamHandler(ApplicationContext context) {
        this.context = context;
    }

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
                return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("You successfuly joined team " + team.toString());
            } catch (IllegalArgumentException e) {
                return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("To select team write one of " + this.teams.toString());
            }
        }
    }
}