package com.pacilkom.feats.scele.latestTime;

import com.pacilkom.feats.scele.Scrapper;

public class TimeScrapper {

    private static final String IDSERVERTIME = "input#block_progress_serverTime";

    public static String getTime() {
        return Scrapper
                .getInstance()
                .getDocument()
                .selectFirst(IDSERVERTIME).text();
    }
}
