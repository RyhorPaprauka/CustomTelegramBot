package by.paprauko.telegramtouristbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TelegramTouristBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(TelegramTouristBotApplication.class, args);
    }
}
