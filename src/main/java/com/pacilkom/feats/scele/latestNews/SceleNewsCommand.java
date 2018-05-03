package com.pacilkom.feats.scele.latestNews;

import com.pacilkom.feats.interfaces.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

public class SceleNewsCommand implements BotCommand {

    public SendMessage execute(Long chatId) throws Exception {
        InlineKeyboardMarkup bubbles = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new LinkedList<>();

        List<Hyperlink> news =  NewsScrapper.getNews();
        for (Hyperlink link : news) {
            List<InlineKeyboardButton> bubble = new LinkedList<>();
            bubble.add(new InlineKeyboardButton()
                    .setText(link.getContent())
                    .setUrl(link.getLink()));
            rowsInline.add(bubble);
        }
        bubbles.setKeyboard(rowsInline);
        return new SendMessage(chatId,"CS SCELE Headline News")
                .setReplyMarkup(bubbles);
    }
}
