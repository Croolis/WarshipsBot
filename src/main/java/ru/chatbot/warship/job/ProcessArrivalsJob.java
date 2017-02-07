package ru.chatbot.warship.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.chatbot.warship.bot.WarshipBot;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Port;
import ru.chatbot.warship.entity.Voyage;
import ru.chatbot.warship.resources.Keyboard;
import ru.chatbot.warship.resources.Message;
import ru.chatbot.warship.service.PlayerService;
import ru.chatbot.warship.service.PortService;
import ru.chatbot.warship.service.ShipService;
import ru.chatbot.warship.service.VoyageService;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by givorenon on 07.02.17.
 */
public class ProcessArrivalsJob extends QuartzJobBean {
    @Autowired
    private WarshipBot warshipBot;

    public void setWarshipBot(WarshipBot warshipBot) {
        this.warshipBot = warshipBot;
    }

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

    @Autowired
    private VoyageService voyageService;

    public void setVoyageService(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<Voyage> arrivedTravelers = voyageService.startHandlingArrivedTravelers();
        for (Voyage voyage : arrivedTravelers) {
            Player player = playerService.getPlayer(voyage.getPlayerId());
            Port port = portService.getPort(voyage.getDestination());
            SendMessage message;
            if (playerService.arrive(player, port)) {
                message = Message.makeReplyMessage(player.getChatId(), Message.getArrivalMessage(port),
                        Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
            } else {
                message = Message.makeReplyMessage(player.getChatId(), Message.getPortTakenBeforeArrivalMessage(port),
                        Keyboard.getKeyboard(Arrays.asList("INFO", "VOYAGE")));
            }
            try {
                warshipBot.sendMessage(message);
            } catch (TelegramApiException e) {

            }
        }
        voyageService.finishHandlingArrivedTravelers();
    }
}
