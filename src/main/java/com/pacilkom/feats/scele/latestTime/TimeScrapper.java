package com.pacilkom.feats.scele.latestTime;

import com.pacilkom.feats.scele.Scrapper;
import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;



public class TimeScrapper {

    private static final String TIME_SERVER = "ntp.ui.ac.id";  

    public static String getTime() {
        return timeLookup();
    }
    
    private static String timeLookup() {
         
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
        TimeInfo timeInfo = timeClient.getTime(inetAddress);
        long returnTime = timeInfo.getReturnTime();
        Date time = new Date(returnTime);
        return "Time from " + TIME_SERVER + ": " ;//+ time;
    }
}
