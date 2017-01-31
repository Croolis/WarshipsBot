package ru.chatbot.warship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.chatbot.warship.service.PlayerService;
import ru.chatbot.warship.service.ShipService;

/**
 * Created by givorenon on 31.01.17.
 */
@Configuration
public class ServiceConfig {
    public ServiceConfig() {
    }

    @Bean
    public PlayerService playerService() {
        return new PlayerService();
    }

    @Bean
    public ShipService shipService() {
        return new ShipService();
    }
}