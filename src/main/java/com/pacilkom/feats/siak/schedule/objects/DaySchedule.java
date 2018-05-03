package com.pacilkom.feats.siak.schedule.objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pacilkom.feats.login.LoginVerifier.CLIENT_ID;

public class DaySchedule {
    private static final JSONObject EN_ID_DAYS = new JSONObject("{\"Monday\":\"Senin\","
            + "\"Tuesday\":\"Selasa\",\"Wednesday\":\"Rabu\",\"Thursday\":\"Kamis\","
            + "\"Friday\":\"Jumat\",\"Saturday\":\"Sabtu\"}");

    private String dayName;
    private String term;
    private String year;
    private String npm;
    private List<ScheduleItem> items;

    public DaySchedule(String dayName, String term, String year, String npm, List<ScheduleItem> items) {
        this.dayName = dayName;
        this.term = term;
        this.year = year;
        this.npm = npm;
        this.items = items;
    }

    public static String translateDay(String dayName) {
        return EN_ID_DAYS.getString(dayName);
    }

    public String getDayName() {
        return dayName;
    }

    public String getTerm() {
        return term;
    }

    public String getYear() {
        return year;
    }

    public String getNpm() {
        return npm;
    }

    public List<ScheduleItem> getItems() {
        return items;
    }

    public static DaySchedule getApiResponse(String accessToken, String npm, String year,
                                             String term, String day) {
        try {
            URL url = new URL("https://api.cs.ui.ac.id/siakngcs/jadwal-list/" + year
                    + "/" + term + "/" + translateDay(day)
                    + "/" + npm + "/?access_token=" + accessToken
                    + "&client_id=" + CLIENT_ID + "&format=json");
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

    private static List<ScheduleItem> parseJson(JSONArray json) {
        List<ScheduleItem> items = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            items.add(ScheduleItem.parseJson(json.getJSONObject(i)));
        }

        return items;
    }

    public String toString() {
        if (items.size() == 0) {
            return "\nHmm... there are no class that you must attend on that day...";
        }

        String result = "";
        for (int i = 0; i < items.size(); i++) {
            result += items.get(i).toString();
        }

        return result;
    }
}
