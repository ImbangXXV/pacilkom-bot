package com.pacilkom.feats.scele.latestNews;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class SceleNewsCommandTest {
    private Long chatId = Long.valueOf(001);
    private SendMessage msg;
    private List<Hyperlink> titles;
    private List<List<InlineKeyboardButton>> news;

    @Before
    public void setUp() throws Exception {
        msg = new SceleNewsCommand().execute(chatId);
        titles = NewsScrapper.getNews();
        news = ((InlineKeyboardMarkup) msg
                .getReplyMarkup()).getKeyboard();
    }

    @Test
    public void hasCorrectNumberOfNews() {
       int size = news.size();
       assertEquals(titles.size(), size);
    }

    @Test
    public void checkTheReplyIsCorrect() {
        int pointer = 0;
        for (List<InlineKeyboardButton> bubble : news) {
            assertTrue(titles.get(pointer)
                    .getContent().equals(bubble.get(0).getText()));
            assertTrue(titles.get(pointer)
                    .getLink().equals(bubble.get(0).getUrl()));
            pointer++;
        }
    }

}