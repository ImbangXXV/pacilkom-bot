package com.pacilkom.feats.scele.latestNews;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class SceleNewsCommandTest {
    private Long chatId = Long.valueOf(001);
    private SendMessage msg;
    private List<Hyperlink> titles;

    @Before
    public void setUp() throws Exception {
        msg = new SceleNewsCommand().execute(chatId);
        titles = NewsScrapper.getNews();
    }

    @Test
    public void hasCorrectNumberOfNews() {
       String[] news = msg.getText().split("\n\n");
       assertEquals(titles.size(), news.length);
    }

    @Test
    public void checkTheReplyIsCorrect() {
        String[] news = msg.getText().split("\n\n");
        int pointer = 0;
        for (Hyperlink title : titles) {
            assertTrue(news[pointer].equals(title.toString()));
            pointer++;
        }
    }

}