package ru.chatbot.warship.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Port;
import ru.chatbot.warship.resources.Message;
import ru.chatbot.warship.service.PlayerService;
import ru.chatbot.warship.service.PortService;

import java.util.List;

/**
 * Created by givorenon on 03.02.17.
 */
public class AttackPreparationHandler implements Handler {
    @Autowired
    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    private PortService portService;

    public void setPortService(PortService portService) {
        this.portService = portService;
    }
    @Override
    public boolean matchCommand(Update update) {
        return update.getMessage().getText().equals("ATTACK");
    }

    @Override
    public SendMessage handle(Update update) {
        Integer userId = update.getMessage().getFrom().getId();
        Player player = playerService.getPlayer(userId);
        List<Port> ports = portService.getEnemyPorts(playerService.getPlayerLocation(player.getId()), player.getTeam());

        try {
            return Message.makeReplyMessage(update, Message.getAttackPreparationMessage(ports),
                    null);
        } catch (IllegalArgumentException e) {
            return Message.makeReplyMessage(update, Message.getSorryMessage());
        }
    }
}
