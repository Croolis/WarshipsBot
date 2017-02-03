package ru.chatbot.warship.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Ship;
import ru.chatbot.warship.resources.Keyboard;
import ru.chatbot.warship.resources.Message;
import ru.chatbot.warship.service.PlayerService;
import ru.chatbot.warship.service.ShipService;

import java.util.Arrays;


/**
 * Created by givorenon on 31.01.17.
 */
public class PlayerInfoHandler implements Handler {
    @Autowired
    private ShipService shipService;

    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @Autowired
    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

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
            try {
                return Message.makeReplyMessage(update, Message.getInfoMessage(player, ship),
                        Keyboard.getKeyboard(Arrays.asList("INFO")));
            } catch (IllegalArgumentException e) {
                return Message.makeReplyMessage(update, Message.getSorryMessage());
            }
        }
    }
}
