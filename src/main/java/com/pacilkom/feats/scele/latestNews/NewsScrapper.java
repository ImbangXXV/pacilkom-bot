package com.pacilkom.feats.scele.latestNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class NewsScrapper {

    public static final String SCELE_LINK = "https://scele.cs.ui.ac.id/";

    public static List<String> getNews() throws IOException {
        Document doc = Jsoup.connect(SCELE_LINK).get();
        List<String> messages = new LinkedList<>();
        Elements block = doc.select("div#site-news-forum");
        for (Element el : block.select("div.forumpost")) {
            Elements text = el.select("div.topic");
            String title = text.select("div.subject").text();
            String author = text.select("div.author").text();
            String link = el.select("div.row.side")
                    .select("div.options")
                    .select("div.link")
                    .select("a").attr("href");
            messages.add("[" + title + " " + author+ "](" + link + ")");
        }
        return messages;
    }

}
