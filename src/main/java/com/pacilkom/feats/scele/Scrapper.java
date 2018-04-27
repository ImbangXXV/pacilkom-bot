package com.pacilkom.feats.scele;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper {
    
    private static Scrapper instance = null;
    private Document scele;
    private final static String SCELE_LINK = "http://scele.cs.ui.ac.id";
    
    private Scrapper() {
        try {
            scele = Jsoup.connect(SCELE_LINK).get();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static Scrapper getInstance() {
        if(instance == null) {
            instance = new Scrapper();
         }
         return instance;
    }
    
    public Document getDocument() {
        return scele;
    }
}
