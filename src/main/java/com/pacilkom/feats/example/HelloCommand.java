package com.pacilkom.feats.example;

import com.pacilkom.feats.interfaces.ParamBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class HelloCommand implements ParamBotCommand{

    public SendMessage execute(Long chatId, String text) throws Exception {
        return new SendMessage(chatId, "Hello, this is your message: " + text);
    }
}
