package ru.miaat;

import org.aeonbits.owner.ConfigFactory;
import ru.miaat.config.Properties;

public class RabbitQueue {

    public static final Properties config = ConfigFactory.create(Properties.class);

//    public static final String DOC_MESSAGE_UPDATE = config.docQueue();
    public static final String DOC_MESSAGE_UPDATE = "doc_message_update";
//    public static final String PHOTO_MESSAGE_UPDATE = config.photoQueue();
    public static final String PHOTO_MESSAGE_UPDATE = "photo_message_update";
//    public static final String TEXT_MESSAGE_UPDATE = config.textQueue();
    public static final String TEXT_MESSAGE_UPDATE = "text_message_update";
    public static final String ANSWER_MESSAGE = "answer_message";
}
