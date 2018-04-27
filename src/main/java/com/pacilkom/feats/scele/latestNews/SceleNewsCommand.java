package com.pacilkom.feats.scele.latestNews;

import com.pacilkom.feats.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SceleNewsCommand implements BotCommand {

    public SendMessage execute(Long chatId) throws IOException {
        List<Hyperlink> replies = NewsScrapper.getNews();
        String reply = replies.stream()
                .map(r -> r.toString())
                .collect(Collectors.joining("\n"));

        return new SendMessage(chatId, reply);

    }
}
