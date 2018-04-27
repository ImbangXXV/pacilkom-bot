package com.pacilkom.feats;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public interface ParamBotCommand {

    public SendMessage execute(Long chatId, String text) throws Exception;
}
