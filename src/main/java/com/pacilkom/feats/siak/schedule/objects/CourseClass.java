package com.pacilkom.feats.siak.schedule.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseClass {
    private String subject;
    private String className;
    private String classCode;
    private List<String> lecturers;
    private int credit;

    public CourseClass(String subject, String className, String classCode,
                       List<String> lecturers, int credit) {
        this.subject = subject;
        this.className = className;
        this.classCode = classCode;
        this.lecturers = lecturers;
        this.credit = credit;
    }

    public String getSubject() {
        return subject;
    }

    public String getClassName() {
        return className;
    }

    public String getClassCode() {
        return classCode;
    }

    public List<String> getLecturers() {
        return lecturers;
    }

    public int getCredit() {
        return credit;
    }

    public static CourseClass parseJson(JSONObject json) {
        JSONObject course = json.getJSONObject("kd_kls_sc");
        JSONObject courseDef = course.getJSONObject("nm_mk_cl");

        String subject = courseDef.getString("nm_mk");
        String className = course.getString("nm_kls");
        String classCode = course.getString("kd_mk_cl");
        int credit = courseDef.getInt("jml_sks");

        JSONArray lecturers = course.getJSONArray("pengajar");
        List<String> lecturerNames = new ArrayList<>();
        for (int i = 0; i < lecturers.length(); i++) {
            lecturerNames.add(lecturers.getJSONObject(i).getString("nama"));
        }

        return new CourseClass(subject, className, classCode, lecturerNames, credit);
    }

    public String toString() {
        String result = className + " (" + classCode + "):\n"
                + "- Course Name: " + subject + "\n"
                + "- Credit: " + credit + " SKS\n"
                + "- Lecturer(s):\n";

        for (String lecturer : lecturers) {
            result += "  > " + lecturer + "\n";
        }

        return result;
    }
}
