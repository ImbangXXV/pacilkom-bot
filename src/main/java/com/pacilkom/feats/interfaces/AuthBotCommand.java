package com.pacilkom.feats.interfaces;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public interface AuthBotCommand {

    SendMessage execute(Long chatId, Integer userId) throws Exception;
}
