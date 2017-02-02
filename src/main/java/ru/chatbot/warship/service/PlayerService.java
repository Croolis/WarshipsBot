package ru.chatbot.warship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Ship;
import ru.chatbot.warship.entity.Team;

/**
 * Created by givorenon on 31.01.17.
 */
public class PlayerService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private ShipService shipService;

    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    private static final String GET_PLAYER_BY_ID_SQL = "select ID, NICKNAME, TEAM, GOLD from PLAYER where ID = ?";
    private static final String INSERT_PLAYER_SQL = "insert into PLAYER (ID, NICKNAME, TEAM, GOLD) values(?, ?, ?, 0)";

    private static final Long DEFAULT_SHIP_ID = 0L;

    public Player getPlayer(Integer id) {
        try {
            return jdbcTemplate.queryForObject(GET_PLAYER_BY_ID_SQL, new Object[]{id}, new Player.PlayerRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void createPlayer(Integer userId, String nickname, Team team) throws DataAccessException {
        jdbcTemplate.update(INSERT_PLAYER_SQL, new Object[]{userId, nickname, team.getTeamId()});
        shipService.createShip("My first ship", userId, DEFAULT_SHIP_ID, getPlayerLocation(userId));
    }

    public Integer getPlayerLocation(Integer id) {
        Player player = getPlayer(id);
        if (player != null) {
            Ship ship = shipService.getEmployedShip(id);
            if (ship != null) {
                return ship.getLocationId();
            } else {
                switch (player.getTeam()) {
                    case BRITAIN:
                        return PortService.getDefaultLocationBritain();
                    case SPAIN:
                        return PortService.getDefaultLocationSpain();
                    default:
                        return null;
                }
            }
        } else {
            return null;
        }
    }
}
