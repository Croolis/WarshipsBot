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

    @Override
    public boolean matchCommand(Update update) {
        return tradePattern.matcher(update.getMessage().getText()).matches();
    }

    @Override
    public SendMessage handle(Update update) {

        try {
            Integer userId = update.getMessage().getFrom().getId();
            Player player = playerService.getPlayer(userId);
            Integer destinationId = Integer.valueOf(update.getMessage().getText().substring(7));
            Port port = portService.getPort(destinationId);
            if (playerService.arrive(player, port)) {
                Long gold = 50L;
                playerService.giveGold(player, gold);
                return Message.makeReplyMessage(update, Message.getArrivalTradeMessage(port, gold),
                        Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
            } else {
                return Message.makeReplyMessage(update, Message.getPortTakenBeforeArrivalMessage(port),
                        Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
            }
        } catch (IllegalArgumentException e) {
            return Message.makeReplyMessage(update, Message.getSorryMessage());
        }
    }
}
