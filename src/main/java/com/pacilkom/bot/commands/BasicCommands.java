package com.pacilkom.bot.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public class BasicCommands {
    private static final BasicCommands INSTANCE = new BasicCommands();

    private BasicCommands() {}

    public static BasicCommands getInstance() {
        // TODO Implement me!
        return INSTANCE;
    }

    public SendMessage hello(Long chatId, String text) {
        return new SendMessage(chatId, "Hello, this is your message: " + text);
    }

    public SendMessage help(Long chatId) {
        String text = "Welcome to Pacilkom Bot! Here are the commands you can use:\n" +
                "/hello <message> - returns hello message (for testing purposes)\n" +
                "/help - returns list of commands and this bot's how-to\n" +
                "/about - show this bot's development credits\n" +
                "You can also chat as usual, not using these commands, and the bot " +
                "can find some keywords between your messages, and shows what you want.";
        return new SendMessage(chatId, text);
    }

    public SendMessage about(Long chatId) {
        String text = "This bot is made by students of Advanced Programming even 2017/2018" +
                "class, Faculty of Computer Science, University of Indonesia.";
        return new SendMessage(chatId, text);
    }

}
