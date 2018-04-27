package com.pacilkom.feats;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.IOException;

public interface BotCommand {

    public SendMessage execute(Long chatId) throws IOException;

}
