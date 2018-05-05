package com.pacilkom.feats.siak.schedule.api;

import com.pacilkom.csui.CSUIAcademic;
import com.pacilkom.feats.siak.schedule.objects.DaySchedule;
import com.pacilkom.feats.siak.schedule.objects.ScheduleItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ScheduleAPI {
    public static final JSONObject EN_ID_DAYS = new JSONObject("{\"Monday\":\"Senin\","
            + "\"Tuesday\":\"Selasa\",\"Wednesday\":\"Rabu\",\"Thursday\":\"Kamis\","
            + "\"Friday\":\"Jumat\",\"Saturday\":\"Sabtu\"}");

    public static DaySchedule getApiResponse(String accessToken, String npm, String year,
                                             String term, String day) {
        try {
            JSONArray json = CSUIAcademic.getScheduleByDay(accessToken, npm, year, term,
                    translateDay(day));
            List<ScheduleItem> items = DaySchedule.parseJson(json);
            return new DaySchedule(day, term, year, npm, items);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String translateDay(String dayName) {
        try {
            return EN_ID_DAYS.getString(dayName);
        } catch (JSONException e) {
            return null;
        }
    }
}
