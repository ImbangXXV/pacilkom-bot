package com.pacilkom.feats.example;

import com.pacilkom.feats.interfaces.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class StartCommand implements BotCommand {

    public SendMessage execute(Long chatId) throws Exception {
        return new SendMessage(chatId, "[WELCOME TO PACILKOM BOT]\n\n" +
                "Welcome to a bot that specifically made for students of Faculty of " +
                "Computer Science, University of Indonesia. But don't worry, not all of " +
                "the features are solely made for the students. It is also suitable for " +
                "all users!\nOkay... if you really need help, use /help command. It " +
                "can list all of the available commands along with their details.");
    }
}
