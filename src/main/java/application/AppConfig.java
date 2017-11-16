package application;

import application.services.game.GameTools;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Date;
import java.util.Random;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    @Qualifier("mymapper")
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public Random getRandom() {
        return new Random(new Date().getTime());
    }

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(GameTools.LOGGER_NAME);
    }
}
