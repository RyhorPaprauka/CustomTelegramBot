package by.paprauko.telegramtouristbot.bot;

import by.paprauko.telegramtouristbot.database.entity.City;
import by.paprauko.telegramtouristbot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final String BOT_NAME = "@my_tourist_bot";
    private static final String BOT_TOKEN = "906502692:AAF5xtlrWWvBnYa3IjrfJy7PV-C4BM-egT4";

    @Autowired
    private CityService cityService;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            String textMessage = message.getText();
            Long chatId = message.getChatId();

            String answer = cityService.getCityByName(textMessage)
                    .map(City::getInformation)
                    .orElse("There is no information about " + textMessage);

            SendMessage sendMessage = new SendMessage()
                    .setChatId(chatId)
                    .setText(answer);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
