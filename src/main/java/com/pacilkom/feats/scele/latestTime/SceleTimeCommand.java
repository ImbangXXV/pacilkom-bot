package com.pacilkom.feats.scele.latestTime;

import com.pacilkom.feats.BotCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public class SceleTimeCommand implements BotCommand {

    @Override
    public SendMessage execute(Long chatId) throws Exception {
        return new SendMessage(chatId, TimeGetter.getTime());
    }
}
