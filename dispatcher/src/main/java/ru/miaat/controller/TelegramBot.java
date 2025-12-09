package ru.miaat.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.name}")
    private String name;

    private UpdateController updateController;

    public TelegramBot(UpdateController controller) {
        this.updateController = controller;
    }

    @PostConstruct
    public void init() {
        updateController.registerBot(this);
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
    
    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Received: {}", update.getMessage().getText());

        updateController.processUpdate(update);
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message == null) {
            return;
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
