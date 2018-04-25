package com.pacilkom.feats.scele.latestNews;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NewsScrapperTest {

    List<String> sample;

    @Test
    public void correctScrappingTest() throws IOException {
        String html = Jsoup.connect(NewsScrapper.SCELE_LINK).get().text();
        sample = NewsScrapper.getNews();

        for (String head : sample) {
            assertTrue(html.contains(head));
        }

    }

}