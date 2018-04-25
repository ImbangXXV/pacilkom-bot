package com.pacilkom.bot;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class timeScrapper {
    private Document scele;
    
    public timeScrapper(String url) {
        try {
            scele = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getTime() {
        return scele.selectFirst("input#block_progress_serverTime").text();
    }
    
}
