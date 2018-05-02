package com.pacilkom.feats.example;

import com.pacilkom.feats.interfaces.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class AboutCommand implements BotCommand {

    public SendMessage execute(Long chatId) {
        return new SendMessage(chatId, "Hello, I am Pacilkom Bot. I am just a bot. " +
                "So, as everybody would expect, I must be made by hoomans. And here are the " +
                "hoomans who made me alive:\n" +
                "Dennis Febri Dien, Ichlasul Affan, Muhammad Imbang Murtito, Rachmat Ridwan, " +
                "and Samuel Tupa Febrian.\n\n" +
                "I also wanna tell ya that I am made using Java 8, with Spring Boot Framework " +
                "and rubenlagus' Telegram Bot API. Tribute for all of them!!!");
    }
}
