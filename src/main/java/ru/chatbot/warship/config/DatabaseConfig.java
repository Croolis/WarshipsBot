package ru.chatbot.warship.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Created by givorenon on 30.01.17.
 */

@Configuration
public class DatabaseConfig {
    public DatabaseConfig() {
    }

    @Bean
    public DataSource dataSource() {
        return (new EmbeddedDatabaseBuilder()).setType(EmbeddedDatabaseType.HSQL).
                addScript("classpath:schema.sql").
                build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource());
    }
}
