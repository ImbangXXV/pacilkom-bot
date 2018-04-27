package com.pacilkom.feats.scele.latestNews;

import com.pacilkom.feats.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.stream.Collectors;

public class SceleNewsCommand implements BotCommand {

    public SendMessage execute(Long chatId) throws Exception {
        return new SendMessage(chatId,
                NewsScrapper.getNews()
                        .stream()
                        .map(n -> n.toString())
                        .collect(Collectors.joining("\n")))
                .enableMarkdown(true);
    }
}
