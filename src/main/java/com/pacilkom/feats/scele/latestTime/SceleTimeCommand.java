package com.pacilkom.feats.scele.latestTime;

import com.pacilkom.feats.interfaces.BotCommand;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public class SceleTimeCommand implements BotCommand {

    @Override
    public SendMessage execute(Long chatId) throws Exception {
        return new SendMessage(chatId, TimeGetter.getTime());
    }
}
