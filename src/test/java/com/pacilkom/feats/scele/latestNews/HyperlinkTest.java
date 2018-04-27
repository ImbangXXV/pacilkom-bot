package com.pacilkom.feats.scele.latestNews;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HyperlinkTest {

    Hyperlink sample;
    String content = "Klik disini";
    String link = "https://example.com";
    String newContent = "Klik disana";
    String newLink = "https://random.org";
    String desc = "[" + content + "](" + link + ")";

    @Before
    public void setUp() {
        System.out.println("Run Hyperlink test...");
        sample = new Hyperlink(content, link);
    }

    @Test
    public void testToString() {
        System.out.println("Testing toString...");
        assertTrue(desc.equals(sample.toString()));

    }

    @Test
    public void testGetters() {
        System.out.println("Testing getter...");
        assertEquals(content, sample.getContent());
        assertEquals(link, sample.getLink());
    }

    @Test
    public void testSetter() {
        System.out.println("Testing setter...");
        sample.setContent(newContent);
        sample.setLink(newLink);
        assertEquals(newContent, sample.getContent());
        assertEquals(newLink, sample.getLink());

    }

}