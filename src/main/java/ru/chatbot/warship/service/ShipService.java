package ru.chatbot.warship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.chatbot.warship.entity.Ship;
import ru.chatbot.warship.entity.ShipType;

/**
 * Created by givorenon on 31.01.17.
 */
public class ShipService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final static String GET_SHIP_BY_ID_SQL = "select s.ID, s.OWNER_ID," +
            "s.NAME, s.SPEED, s.POWER, s.TONNAGE, " +
            "st.NAME as TYPE_NAME, s.EMPLOYED, s.LOCATION " +
            "from ship s, ship_type st " +
            "where s.id = ? and s.type_id = st.id";
    private final static String GET_EMPLOYED_SHIP_SQL = "select s.ID, s.OWNER_ID," +
            "s.NAME, s.SPEED, s.POWER, s.TONNAGE, " +
            "st.NAME as TYPE_NAME, s.EMPLOYED, s.LOCATION " +
            "from ship s, ship_type st " +
            "where s.type_id = st.id " +
            "and s.owner_id = ? " +
            "and s.employed = 1";
    private final static String INSERT_SHIP_SQL = "insert into SHIP (OWNER_ID, NAME, TYPE_ID, SPEED, POWER, " +
            "TONNAGE, EMPLOYED, LOCATION) values(?, ?, ?, ?, ?, ?, 1, ?)";
    private final static String UNEMPLOY_SHIP_SQL = "update SHIP set EMPLOYED = 0 " +
            "where owner_id = ? " +
            "and employed = 1";
    private final static String GET_SHIP_TYPE_SQL = "select ID, NAME, MEAN_SPEED, SPEED_DEVIATION, " +
            "MEAN_POWER, POWER_DEVIATION, MEAN_TONNAGE, TONNAGE_DEVIATION from SHIP_TYPE where ID = ?";

    public Ship getShip(Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_SHIP_BY_ID_SQL, new Object[]{id}, new Ship.ShipRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * returns ship which is currently using by player
    **/
    public Ship getEmployedShip(Integer userId) {
        try {
            return jdbcTemplate.queryForObject(GET_EMPLOYED_SHIP_SQL, new Object[]{userId}, new Ship.ShipRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void createShip(String shipName, Integer ownerId, Long typeId, Long locationId) {
        ShipType shipType = jdbcTemplate.queryForObject(GET_SHIP_TYPE_SQL, new Object[]{typeId}, new ShipType.ShipTypeRowMapper());
        Long speed =  shipType.getMeanSpeed() + (long)((Math.random() - 0.5) * 2 * shipType.getSpeedDeviation());
        Long power = shipType.getMeanPower() + (long)((Math.random() - 0.5) * 2 * shipType.getPowerDeviation());
        Long tonnage = shipType.getMeanTonnage() + (long)((Math.random() - 0.5) * 2 * shipType.getTonnageDeviation());
        jdbcTemplate.update(UNEMPLOY_SHIP_SQL, ownerId);
        jdbcTemplate.update(INSERT_SHIP_SQL, new Object[]{ownerId, shipName, typeId,
                speed, power, tonnage, locationId});
    }
}
