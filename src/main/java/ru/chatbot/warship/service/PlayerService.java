package ru.chatbot.warship.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.chatbot.warship.bot.WarshipBot;
import ru.chatbot.warship.config.DatabaseConfig;
import ru.chatbot.warship.config.ServiceConfig;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Ship;
import ru.chatbot.warship.entity.Team;

/**
 * Created by givorenon on 31.01.17.
 */
public class PlayerService {
    private JdbcTemplate jdbcTemplate = (JdbcTemplate) WarshipBot.context.getBean("jdbcTemplate");
    private ShipService shipService = (ShipService) WarshipBot.context.getBean("shipService");

    private static final String GET_PLAYER_BY_ID_SQL = "select ID, NICKNAME, TEAM, GOLD from PLAYER where ID = ?";
    private static final String INSERT_PLAYER_SQL = "insert into PLAYER (ID, NICKNAME, TEAM, GOLD) values(?, ?, ?, 0)";

    private static final Long DEFAULT_LOCATION_BRITAIN = 1L;
    private static final Long DEFAULT_LOCATION_SPAIN = 2L;
    private static final Long DEFAULT_SHIP_ID = 1L;

    public Player getPlayer(Integer id) {
        try {
            return this.jdbcTemplate.queryForObject(GET_PLAYER_BY_ID_SQL, new Object[]{id}, new Player.PlayerRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void createPlayer(Integer userId, String nickname, Team team) throws DataAccessException {
        this.jdbcTemplate.update(INSERT_PLAYER_SQL, new Object[]{userId, nickname, team.getTeamId()});
        shipService.createShip("My first ship", userId, DEFAULT_SHIP_ID, getPlayerLocation(userId));
    }

    public Long getPlayerLocation(Integer id) {
        Player player = getPlayer(id);
        if (player != null) {
            Ship ship = shipService.getEmployedShip(id);
            if (ship != null) {
                return ship.getLocationId();
            } else {
                switch (player.getTeam()) {
                    case BRITAIN:
                        return DEFAULT_LOCATION_BRITAIN;
                    case SPAIN:
                        return DEFAULT_LOCATION_SPAIN;
                    default:
                        return null;
                }
            }
        } else {
            return null;
        }
    }
}
