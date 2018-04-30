package com.pacilkom.feats.scele.latestTime;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;


public class TimeGetter {

    private static final String TIME_SERVER = "ntp.ui.ac.id";



    public static String getTime() {
        return timeLookup();
    }

    private static String timeLookup() {

        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getReturnTime();
            Date time = new Date(returnTime);
            return "Time from " + TIME_SERVER + ": " + time;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
