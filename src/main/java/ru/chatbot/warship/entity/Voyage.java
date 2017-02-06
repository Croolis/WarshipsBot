package ru.chatbot.warship.entity;

import org.hsqldb.HsqlDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by ospen on 2/6/2017.
 */
public class Voyage {
    private Integer playerId;
    private Integer leaderId;
    private Timestamp startTime;
    private Timestamp finishTime;
    private Integer type; // TODO:should be VoyageType
    private Integer reward;

    Voyage(Integer playerId, Integer leaderId, Timestamp startTime, Timestamp finishTime, Boolean finished, Integer type, Integer reward) {
        this.playerId = playerId;
        this.leaderId = leaderId;
        this.finishTime = finishTime;
        this.startTime = startTime;
        this.finished = finished;
        this.type = type;
        this.reward = reward;
    }
    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    private Boolean finished;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }



    public static class VoyageRowMapper implements RowMapper<Voyage> {
        public VoyageRowMapper() {
        }

        public Voyage mapRow(ResultSet rs, int rowNum) {
            try {
                return new Voyage(rs.getInt("PLAYER_ID"), rs.getInt("LEADER_ID"),
                        rs.getTimestamp("START_TIME"), rs.getTimestamp("FINISH_TIME"),
                        rs.getBoolean("FINISHED"), rs.getInt("TYPE"), rs.getInt("REWARD"));
            } catch (SQLException e) {
                return null;
            }
        }
    }
}
