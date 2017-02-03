package ru.chatbot.warship.resources;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ospen on 2/3/2017.
 */
public class Message {

    public static ReplyKeyboardMarkup getKeyboard(List<String> buttons){
        int rows = (buttons.size() - 1) / 3 + 1;
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        int buttonCount = buttons.size() / rows;
        for (int i = 0; i < rows; i++) {
            keyboardRows.add(new KeyboardRow());
            for (int j = 0; j < buttonCount; j++) {
                keyboardRows.get(i).add(buttons.get(i * buttonCount + j));
            }
        }
        int lastIndex = (rows - 1) * buttonCount + buttonCount ;
        while (lastIndex < buttons.size()) {
            keyboardRows.get(rows - 1).add(buttons.get(lastIndex));
            lastIndex++;
        }
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboad(true);
        return keyboardMarkup;
    }

    public static String SORRY_MESSAGE = "Unavailable command";

    public static String CREDITS = "This game is written by @givorenon @ilyailya @LevOspennikov";

    public static String getJoinTeamMessage(String team) {
        return "You successfully joined team " + team;
    }

    public static String getSelectTeamMessage(String team) {
        return "To select team write one of " + team;
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

}