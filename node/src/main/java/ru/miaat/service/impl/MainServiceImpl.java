package ru.miaat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.miaat.dao.RawDataDAO;
import ru.miaat.entity.RawData;
import ru.miaat.service.MainService;
import ru.miaat.service.ProducerService;


@Service
@Slf4j
public class MainServiceImpl implements MainService {

    private final RawDataDAO rawDataDAO;

    private final ProducerService producerService;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        Message message = update.getMessage();
        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text("Hello from " + this.getClass().getName())
                .build();

        producerService.produceAnswer(sendMessage);
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();

        rawDataDAO.save(rawData);
    }
}
