package com.pacilkom.feats.siak.schedule.api;

import com.pacilkom.feats.siak.schedule.objects.DaySchedule;
import com.pacilkom.feats.siak.schedule.objects.ScheduleItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.pacilkom.bot.PacilkomBot.CSUI_CLIENT_ID;

public class ScheduleAPI {
    public static final JSONObject EN_ID_DAYS = new JSONObject("{\"Monday\":\"Senin\","
            + "\"Tuesday\":\"Selasa\",\"Wednesday\":\"Rabu\",\"Thursday\":\"Kamis\","
            + "\"Friday\":\"Jumat\",\"Saturday\":\"Sabtu\"}");

    public static DaySchedule getApiResponse(String accessToken, String npm, String year,
                                             String term, String day) {
        try {
            URL url = new URL("https://api.cs.ui.ac.id/siakngcs/jadwal-list/" + year
                    + "/" + term + "/" + translateDay(day)
                    + "/" + npm + "/?access_token=" + accessToken
                    + "&client_id=" + CSUI_CLIENT_ID + "&format=json");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";

            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }

            rd.close();
            List<ScheduleItem> items = DaySchedule.parseJson(new JSONArray(result));
            return new DaySchedule(day, term, year, npm, items);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String translateDay(String dayName) {
        return EN_ID_DAYS.getString(dayName);
    }
}
