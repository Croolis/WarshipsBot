package ru.chatbot.warship.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.bot.WarshipBot;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Ship;
import ru.chatbot.warship.service.PlayerService;
import ru.chatbot.warship.service.ShipService;


/**
 * Created by givorenon on 31.01.17.
 */
public class PlayerInfoHandler implements Handler {
    private PlayerService playerService = (PlayerService) WarshipBot.context.getBean("playerService");
    private ShipService shipService = (ShipService) WarshipBot.context.getBean("shipService");

    private boolean matchCommand(Update update) {
        return update.getMessage().getText().equals("INFO");
    }

    @Override
    public SendMessage handle(Update update) {
        if(!this.matchCommand(update)) {
            return null;
        } else {
            Integer userID = update.getMessage().getFrom().getId();
            Player player = playerService.getPlayer(userID);
            Ship ship = shipService.getEmployedShip(userID);
            String msg = "Your nickname: " + player.getNickname() + "\n" +
                    "Your team: " + player.getTeam().toString() + "\n" +
                    "Your ship:" + "\n" +
                    "    Power:   " + ship.getPower().toString() + "\n" +
                    "    Speed:   " + ship.getSpeed().toString() + "\n" +
                    "    Tonnage: " + ship.getTonnage().toString() + "\n" +
                    "    Type: " + ship.getTypeName();
            try {
                return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText(msg);
            } catch (IllegalArgumentException e) {
                return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("Sorry but there is nothing we can do");
            }
        }
    }
}
