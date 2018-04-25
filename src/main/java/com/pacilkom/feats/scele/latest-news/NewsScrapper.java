import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsScrapper {

    public static final String SCELE_LINK = "https://scele.cs.ui.ac.id/";

    public static String getNews() throws IOException {
        Document doc = Jsoup.connect(SCELE_LINK).get();
        String output = "";
        Elements block = doc.select("div#site-news-forum");
        for (Element el : block.select("div.forumpost")) {
            output += el.attr("aria-label")+"\n";
        }
        return output;
    }

    public static void main (String[] args) throws IOException {
        System.out.println(getNews());
    }

}
