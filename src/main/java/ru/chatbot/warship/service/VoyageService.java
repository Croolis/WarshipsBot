package ru.chatbot.warship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.chatbot.warship.entity.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ospen on 2/6/2017.
 */
public class VoyageService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final static String GET_VOYAGE_BY_ID_SQL = "select PLAYER_ID, LEADER_ID, START_TIME, FINISH_TIME, " +
            "FINISHED, TYPE, REWARD from VOYAGE where PLAYER_ ID = ?";

    private final static String INSERT_VOYAGE_SQL = "insert into VOYAGE ( PLAYER_ID, LEADER_ID, START_TIME, FINISH_TIME," +
            "FINISHED, TYPE, REWARD) values(?, ?, ?, ?, ?, ?)";

    private final static String GET_PLAYERS_WITH_SAME_LEADER = "select PLAYER_ID from VOYAGE where (? = LEADER_ID)";

    private final static String GET_ARRIVED_PLAYERS = "select PLAYER_ID from VOYAGE where (? > FINISH_TIME)";

    private final static String UPDATE_ARRIVED_PLAYERS = "update VOYAGE set FINISHED = 1 where (? > FINISH_TIME)";

    public Voyage getVoyage(Player player) {
        try {
            return jdbcTemplate.queryForObject(GET_VOYAGE_BY_ID_SQL, new Object[]{player.getId()}, new Voyage.VoyageRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void createVoyage(List<Player> playerList, Player leader, Integer type, Long duration) {
        Timestamp startTime = new Timestamp(System.currentTimeMillis() ); // maybe not good decision
        Timestamp finishTime = new Timestamp(System.currentTimeMillis() + duration);
        Integer reward = null; // Will store in type
        for (Player player : playerList) {
            jdbcTemplate.update(INSERT_VOYAGE_SQL, player.getId(), leader.getId(), startTime, finishTime, type, null);
        }
    }

    public List<Integer> getArrivedPlayers() {
        try {
            Timestamp current_time = new Timestamp(System.currentTimeMillis());
            jdbcTemplate.update(UPDATE_ARRIVED_PLAYERS, current_time);
            return jdbcTemplate.queryForList(GET_ARRIVED_PLAYERS, new Object[]{current_time}).stream()
                    .map(rs -> ((Integer)rs.get("PLAYER_ID"))).collect(Collectors.toList());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<Integer> getPlayersWithSameLeader(Player player) {
        try {
            return jdbcTemplate.queryForList(GET_PLAYERS_WITH_SAME_LEADER, new Object[]{player.getId()}).stream()
                    .map(rs -> ((Integer)rs.get("PLAYER_ID"))).collect(Collectors.toList());
        } catch (DataAccessException e) {
            return null;
        }
    }
}
