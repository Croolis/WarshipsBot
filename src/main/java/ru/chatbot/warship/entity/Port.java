package ru.chatbot.warship.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by givorenon on 03.02.17.
 */
public class Port {
    Integer id;
    String name;
    Integer x;
    Integer y;
    Team owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Team getOwner() {
        return owner;
    }

    public void setOwner(Team owner) {
        this.owner = owner;
    }

    public Port(Integer id, String name, Integer x, Integer y, Team owner) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public static class PortRowMapper implements RowMapper<Port> {
        public PortRowMapper() {
        }

        public Port mapRow(ResultSet rs, int rowNum) {
            try {
                return new Port(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("X"),
                        rs.getInt("Y"), Team.valueOf(rs.getInt("OWNER")));
            } catch (SQLException e) {
                return null;
            }
        }
    }
}
