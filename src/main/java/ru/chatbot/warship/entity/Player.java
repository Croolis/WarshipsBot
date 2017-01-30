package ru.chatbot.warship.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.chatbot.warship.entity.Team;

public class Player {
    Integer id;
    String nickname;
    Team team;
    Long gold;

    public Player(Integer id, String nickname, Team team, Long gold) {
        this.id = id;
        this.nickname = nickname;
        this.team = team;
        this.gold = gold;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getGold() {
        return this.gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public static class PlayerRowMapper implements RowMapper<Player> {
        public PlayerRowMapper() {
        }

        public Player mapRow(ResultSet rs, int rowNum) {
            try {
                return new Player(rs.getInt("ID"), rs.getString("NICKNAME"),
                        Team.valueOf(rs.getInt("TEAM")), rs.getLong("GOLD"));
            } catch (SQLException e) {
                return null;
            }
        }
    }
}