package com.pacilkom.feats.siak.schedule.objects;

import org.json.JSONObject;

import java.time.LocalTime;

public class ScheduleItem {
    private CourseClass course;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomName;

    public ScheduleItem(CourseClass course, LocalTime startTime, LocalTime endTime, String roomName) {
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomName = roomName;
    }

    public CourseClass getCourse() {
        return course;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public static ScheduleItem parseJson(JSONObject json) {
        CourseClass course = CourseClass.parseJson(json);
        LocalTime startTime = LocalTime.parse(json.getString("jam_mulai"));
        LocalTime endTime = LocalTime.parse(json.getString("jam_selesai"));

        JSONObject room = json.getJSONObject("id_ruang");
        String roomName = room.getString("nm_ruang");

        return new ScheduleItem(course, startTime, endTime, roomName);
    }

    public String toString() {
        String result = "\n" + startTime.toString() + " - " + endTime.toString() + "\n"
                + "- Course: " + course.getSubject() + " (" + course.getClassCode() + ")\n"
                + "- Class: " + course.getClassName() + "\n"
                + "- Room: " + roomName + "\n"
                + "- Lecturer(s):\n";

        for (String lecturer : course.getLecturers()) {
            result += "  > " + lecturer + "\n";
        }

        return result;
    }
}
