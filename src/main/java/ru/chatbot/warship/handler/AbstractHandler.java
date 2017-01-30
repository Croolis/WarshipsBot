package ru.chatbot.warship.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.api.objects.Update;
import ru.chatbot.warship.config.DatabaseConfig;
import ru.chatbot.warship.entity.Player;
import ru.chatbot.warship.entity.Player.PlayerRowMapper;

/**
 * Created by givorenon on 30.01.17.
 */


public abstract class AbstractHandler implements Handler {
    protected ApplicationContext context = new AnnotationConfigApplicationContext(new Class[]{DatabaseConfig.class});
    protected JdbcTemplate jdbcTemplate;
    private static final String GET_PLAYER_BY_ID_SQL = "select ID, NICKNAME, TEAM, GOLD from PLAYER where ID = ?";

    public AbstractHandler() {
        this.jdbcTemplate = (JdbcTemplate)this.context.getBean("jdbcTemplate");
    }

    protected Player getPlayer(Update update) {
        try {
            Integer e = update.getMessage().getFrom().getId();
            return this.jdbcTemplate.queryForObject(GET_PLAYER_BY_ID_SQL, new Object[]{e}, new PlayerRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }
}
