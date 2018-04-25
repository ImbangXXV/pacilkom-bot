package com.pacilkom.feats.scele.latestNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewsScrapperTest {

    List<Hyperlink> sample;
    Document doc;

    @Before
    public void setUp() throws IOException {
        System.out.println("Run Newscrapper test...");
        sample = NewsScrapper.getNews();
        doc = Jsoup.connect(NewsScrapper.SCELE_LINK).get();
    }

    @Test
    public void correctScrappingTest() throws IOException {
        System.out.println("Testing content...");
        String html = Jsoup.connect(NewsScrapper.SCELE_LINK).get().text();

        for (Hyperlink head : sample) {
            assertTrue(html.contains(head.getContent()));
        }
    }

    @Test
    public void correctNumberOfNewsTest() throws IOException {
        System.out.println("Testing number of news...");
        Elements nodes = doc.select("div#site-news-forum");
        int count = 0;
        for (Element el : nodes.select("div.forumpost")) {
            count++;
        }
        assertEquals(count, sample.size());
    }

}