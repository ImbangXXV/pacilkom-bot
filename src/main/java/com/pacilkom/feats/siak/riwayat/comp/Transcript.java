package com.pacilkom.feats.siak.riwayat.comp;

import org.json.JSONObject;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
        JSONObject collClass = (JSONObject) json.get("kelas");
        JSONObject subjectInfo = (JSONObject) collClass.get("nm_mk_cl");
        JSONObject lecturer = (JSONObject) collClass.get("pengajar");
        int credit = subjectInfo == null ? 0 :
                subjectInfo.getInt("jml_sks");
        String lectName = lecturer == null ? "No Data" :
                lecturer.getString("nama");
        return new Transcript(subjectInfo.getString("nm_mk"),
              lectName, credit, json.getInt("tahun"),
                json.getInt("term"), json.getString("nilai"));
    }
}
