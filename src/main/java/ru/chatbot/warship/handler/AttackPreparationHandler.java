package ru.chatbot.warship.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Port;
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
        String msg = "Choose port to attack:" + "\n";
        for (Port port : ports) {
            msg += "To attack port " + port.getName() + " write /attack_" + port.getId() + "\n";
        }
        try {
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText(msg);
        } catch (IllegalArgumentException e) {
            return (new SendMessage()).setChatId(update.getMessage().getChatId()).setText("Sorry but there is nothing we can do");
        }
    }
}
