package ru.chatbot.warship.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Port;
import ru.chatbot.warship.resources.Keyboard;
import ru.chatbot.warship.resources.Message;
import ru.chatbot.warship.service.PlayerService;
import ru.chatbot.warship.service.PortService;
import ru.chatbot.warship.service.ShipService;
import ru.chatbot.warship.service.VoyageService;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by givorenon on 03.02.17.
 */
public class TradeHandler implements Handler {
    private Pattern tradePattern = Pattern.compile("\\/trade_(\\d)+");

    @Autowired
    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    private ShipService shipService;

    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @Autowired
    private PortService portService;

    public void setPortService(PortService portService) {
        this.portService = portService;
    }

    @Autowired
    private VoyageService voyageService;

    public void setVoyageService(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @Override
    public boolean matchCommand(Update update) {
        return tradePattern.matcher(update.getMessage().getText()).matches();
    }

    @Override
    public SendMessage handle(Update update) {

        Integer userId = update.getMessage().getFrom().getId();
        Player player = playerService.getPlayer(userId);
        Integer destinationId = Integer.valueOf(update.getMessage().getText().substring(7));
        Port port = portService.getPort(destinationId);
        if (playerService.getPlayerLocation(player.getId()).equals(port.getId())) {
            return Message.makeReplyMessage(update, Message.getAlreadyHereMessage(port),
                    Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
        }
        if (port == null) {
            return Message.makeReplyMessage(update, Message.getNoSuchPortMessage(),
                    Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
        }
        if (!port.getOwner().equals(player.getTeam())) {
            return Message.makeReplyMessage(update, Message.getTradeEnemyPort(),
                    Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
        }

        try {
            voyageService.createTrade(player, player, playerService.getPlayerLocation(player.getId()), destinationId);
            return Message.makeReplyMessage(update, Message.getTradeStartedMessage());
        } catch (IllegalArgumentException e) {
            return Message.makeReplyMessage(update, Message.getSorryMessage());
        }
    }
}
