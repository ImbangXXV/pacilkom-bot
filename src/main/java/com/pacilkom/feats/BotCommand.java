package com.pacilkom.feats;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public interface BotCommand {

    public SendMessage execute(Long chatId) throws Exception;

}
