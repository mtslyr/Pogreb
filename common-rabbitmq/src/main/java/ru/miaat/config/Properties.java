package ru.miaat.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:application.properties")
public interface Properties extends Config {

    @Key("rabbit.queue.doc")
    String docQueue();

    @Key("rabbit.queue.photo")
    String photoQueue();

    @Key("rabbit.queue.text")
    String textQueue();

    @Key("rabbit.queue.answer")
    String answerQueue();
}
