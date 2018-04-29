package com.pacilkom.feats.interfaces;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public interface ParamWithAuthBotCommand {
    SendMessage execute(Long chatId, Integer userId, String text) throws Exception;
}
