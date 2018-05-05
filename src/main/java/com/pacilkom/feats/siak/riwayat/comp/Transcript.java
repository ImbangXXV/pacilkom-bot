package com.pacilkom.feats.siak.riwayat.comp;

import org.json.JSONArray;
import org.json.JSONObject;

public class Transcript {
    private int id;
    private String subject;
    private String lecturer;
    private int credit;
    private int year;
    private int term;
    private String grade;

    public Transcript(int id, String subject, String lecturer, int credit,
                      int year, int term, String grade) {
        this.id = id;
        this.subject = subject;
        this.lecturer = lecturer;
        this.credit = credit;
        this.year = year;
        this.term = term;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Course : " + getSubject()
                + "\nLecturer : " + getLecturer()
                + "\nYear : " + getYear()
                + "\nTerm : " + getTerm()
                + "\nCredit : " + getCredit()
                + "\nGrade : " + getGrade();
    }

    public static Transcript convertJson(JSONObject json) {
        JSONObject collClass = json.getJSONObject("kelas");
        JSONObject subjectInfo = collClass.getJSONObject("nm_mk_cl");
        JSONArray lecturer = collClass.getJSONArray("pengajar");
        int credit = subjectInfo == null ? 0 :
                subjectInfo.getInt("jml_sks");
        String lectName = "";
        if (lecturer == null) {
            lectName = "Unidentified";
        } else {
            for (int i = 0;i < lecturer.length();i++) {
                JSONObject lect = lecturer.getJSONObject(i);
                lectName += lect.isNull("nama") ? "Unidentified"
                        : lect.getString("nama");
                lectName += i < lecturer.length() -1 ? ", " : "";
            }
        }

        return new Transcript(collClass.getInt("kd_kls"),
                subjectInfo.getString("nm_mk"),
                lectName, credit, json.getInt("tahun"),
                json.getInt("term"), json.getString("nilai"));
    }

    public String review() {
        String report = "Alright, this is the report of " + getSubject()
                + " course";
        report += toString();

        if (report.contains("Unidentified")) {
            report += "We're sorry that some data are missing... That's all "
                    + "we got from our source...";
        } else {
            report += "Alright, that should be all.";
        }

        return report;

    }
}