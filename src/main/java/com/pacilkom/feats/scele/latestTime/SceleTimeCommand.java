package com.pacilkom.feats.scele.latestTime;

import com.pacilkom.feats.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class SceleTimeCommand implements BotCommand {

    @Override
    public SendMessage execute(Long chatId) throws Exception {
        SendMessage reply = new SendMessage(chatId, TimeScrapper.getTime());
        reply.enableMarkdown(true);
        return reply;
    }
}
