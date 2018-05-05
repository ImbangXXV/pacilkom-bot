package com.pacilkom.feats.siak.schedule.objects;

import com.pacilkom.feats.siak.schedule.api.ScheduleAPI;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DaySchedule {

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

    public static List<ScheduleItem> parseJson(JSONArray json) {
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
