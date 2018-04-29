package com.pacilkom.feats.interfaces;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public interface ParamBotCommand {
    SendMessage execute(Long chatId, String text) throws Exception;
}
