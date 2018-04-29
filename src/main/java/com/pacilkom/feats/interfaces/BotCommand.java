package com.pacilkom.feats.interfaces;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public interface BotCommand {

    SendMessage execute(Long chatId) throws Exception;

}
