package com.pacilkom.feats.siak.riwayat.comp;

import org.json.JSONArray;
import org.json.JSONObject;

public class Transcript {
    private String subject;
    private String lecturer;
    private int credit;
    private int year;
    private int term;
    private String grade;

    public Transcript(String subject, String lecturer, int credit,
                      int year, int term, String grade) {
        this.subject = subject;
        this.lecturer = lecturer;
        this.credit = credit;
        this.year = year;
        this.term = term;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String toString() {
        return "Subject : " + getSubject()
                + "\nLecturer : " + getLecturer()
                + "\nCredit : " + getCredit()
                + "\nGrade : " + getGrade();
    }
    public static Transcript convertJson(JSONObject json) {
        JSONObject collClass = json.getJSONObject("kelas");
        JSONObject subjectInfo = collClass.getJSONObject("nm_mk_cl");
        JSONArray lecturer = collClass.getJSONArray("pengajar");
        int credit = subjectInfo == null ? 0 :
                subjectInfo.getInt("jml_sks");
        String subjectName = subjectInfo == null ?
                "Unidentified" : subjectInfo.getString("nama");
        String lectName = "";
        if (lecturer != null) {
            for (int i = 0;i < lecturer.length();i++) {
                JSONObject lect = lecturer.getJSONObject(i);
                lectName += lect.isNull("nama") ? "Unidentified"
                        : lect.getString("nama");
                lectName += i < lecturer.length() -1 ? ", " : "";
            }
        } else {
            lectName += "Unidentified";
        }
        return new Transcript(subjectName, lectName, credit,
                json.getInt("tahun"), json.getInt("term"),
                json.getString("nilai"));
    }
}
