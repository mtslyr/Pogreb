package ru.miaat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.miaat.service.UpdateProducer;
import ru.miaat.service.impl.UpdateProducerImpl;
import ru.miaat.util.MessageUtils;

import static ru.miaat.RabbitQueue.*;

@Component
@Slf4j
public class UpdateController {
    private TelegramBot bot;

    private MessageUtils messageUtils;
    private UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer producer) {
        this.messageUtils = messageUtils;
        this.updateProducer = producer;
    }

    public void registerBot(TelegramBot bot) {
        this.bot = bot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received null update!");
            return;
        }

        if (update.getMessage() != null) {
            distributeMessageByType(update);
        } else {
            log.error("Received blank message! " + update);
        }
    }

    private void distributeMessageByType(Update update) {
        Message message = update.getMessage();

        if (message.getText() != null) {
            processTextMessage(update);
        } else if (message.getDocument() != null) {
            processDocMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        } else {
            sendUnsupportedMessageType(update);
        }
    }

    private void sendUnsupportedMessageType(Update update) {
        SendMessage response = messageUtils
                .generateSendMessageWithText(update, "Неподдерживаемый тип сообщения");
        setView(response);
    }

    private void sendContentReceivedMessage(Update update) {
        SendMessage response = messageUtils
                .generateSendMessageWithText(
                        update,
                        "Контент обрабатывается..."
                );

        setView(response);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(
                PHOTO_MESSAGE_UPDATE,
                update
        );

        sendContentReceivedMessage(update);
    }

    private void processDocMessage(Update update) {
        updateProducer.produce(
                DOC_MESSAGE_UPDATE,
                update
        );

        sendContentReceivedMessage(update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(
                TEXT_MESSAGE_UPDATE,
                update
        );
    }

    private void setView(SendMessage message) {
        bot.sendAnswerMessage(message);
    }
}
