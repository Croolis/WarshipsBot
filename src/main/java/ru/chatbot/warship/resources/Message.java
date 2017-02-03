package ru.chatbot.warship.resources;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Ship;
import ru.chatbot.warship.entity.Team;

import java.util.List;

/**
 * Created by ospen on 2/3/2017.
 */
public class Message {
    private static final String SORRY_MESSAGE = "We so sorry, but we can't do anything with that. Really apologize. Please, forgive us.";

    private static final String CREDITS = "This game is written by @givorenon @ilyailya @LevOspennikov";

    public static String getSorryMessage() {
        return SORRY_MESSAGE;
    }

    public static String getCreditsMessage() {
        return CREDITS;
    }

    public static String getJoinTeamMessage(Team team) {
        return "You successfully joined team " + team.toString();
    }

    public static String getSelectTeamMessage(List<Team> teams) {
        return "To select team write one of " + teams.toString();
    }

    public static String getInfoMessage(Player player, Ship ship) {
        return "Your nickname: " + player.getNickname() + "\n" +
                "Your team: " + player.getTeam().toString() + "\n" +
                "Your ship:" + "\n" +
                "    Power:   " + ship.getPower().toString() + "\n" +
                "    Speed:   " + ship.getSpeed().toString() + "\n" +
                "    Tonnage: " + ship.getTonnage().toString() + "\n" +
                "    Type: " + ship.getTypeName();
    }

    public static SendMessage makeReplyMessage(Update update, String message) {
        return new SendMessage().setChatId(update.getMessage().getChatId()).setText(message);
    }

    public static SendMessage makeReplyMessage(Update update, String message, ReplyKeyboard keyboard) {
        return makeReplyMessage(update, message).setReplyMarkup(keyboard);
    }

    public static SendMessage makeMessage(Long chatId, String message) {
        return new SendMessage().setChatId(chatId).setText(message);
    }

    public static SendMessage makeMessage(Long chatId, String message, ReplyKeyboard keyboard) {
        return makeMessage(chatId, message).setReplyMarkup(keyboard);
    }
}