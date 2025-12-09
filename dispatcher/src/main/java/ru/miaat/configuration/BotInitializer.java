package ru.miaat.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.miaat.controller.TelegramBot;

@Configuration
@Slf4j
public class BotInitializer {

    private final TelegramBot bot;

    @Autowired
    public BotInitializer(TelegramBot bot) {
        this.bot = bot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        try {
            api.registerBot(bot);
            log.info("Bot started!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
