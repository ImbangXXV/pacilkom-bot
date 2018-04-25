package com.pacilkom.feats.scele.latestNews;

public class Hyperlink {
    private String content;
    private String link;

    public Hyperlink(String content, String link) {
        this.content = content;
        this.link = link;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return "[" + content + "](" + link + ")";
    }
}
