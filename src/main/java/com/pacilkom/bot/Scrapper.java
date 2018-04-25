package com.pacilkom.bot;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper {
    
    private static Scrapper instance = null;
    private Document scele;
    private final String url = "http://scele.cs.ui.ac.id";
    
    private Scrapper() {
        
    }
    
    public static Scrapper getInstance() {
        if(instance == null) {
            instance = new Scrapper();
         }
         return instance;
    }
    
    public String getTime() {
        return scele.selectFirst("input#block_progress_serverTime").text();
    }
}
