package com.pacilkom.feats.siak.riwayat.comp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TranscriptTest {

    private int id = 11;
    private int id2 = 111;
    private String courseName = "Dasar-Dasar Laravel Coding";
    private String courseName2 = "Dasar-Dasar Laravel Coding";
    private String lecturer = "Pak Thanos M. Kom";
    private String lecturer2 = "Pak Thanos M. Kom";
    private int credit = 3;
    private int credit2 = 2;
    private int year = 2017;
    private int year2 = 2016;
    private int term = 1;
    private int term2 = 2;
    private String grade = "A";
    private String grade2 = "C";

    private Transcript sample;

    @Before
    public void setUp() {
        sample = new Transcript(id, courseName, lecturer, credit,
                year, term, grade);
    }

    @Test
    public void testSetterGetterWorkedProperly() {
        sample.setId(id2);
        assertEquals(id2, sample.getId());
        sample.setSubject(courseName2);
        assertTrue(sample.getSubject().equals(courseName2));
        sample.setLecturer(lecturer2);
        assertTrue(sample.getLecturer().equals(lecturer2));
        sample.setCredit(credit2);
        assertEquals(credit2, sample.getCredit());
        sample.setYear(year2);
        assertEquals(year2, sample.getYear());
        sample.setTerm(term2);
        assertEquals(term2, sample.getTerm());
        sample.setGrade(grade2);
        assertTrue(sample.getGrade().equals(grade2));
    }

    @Test
    public void testReviewMethodWorkingProperly() {
        String review = sample.review();
        assertTrue(review.contains(String.valueOf(id)));
        assertTrue(review.contains(courseName));
        assertTrue(review.contains(lecturer));
        assertTrue(review.contains(String.valueOf(credit)));
        assertTrue(review.contains(String.valueOf(year)));
        assertTrue(review.contains(String.valueOf(term)));
        assertTrue(review.contains(String
                .valueOf(GradeMapper.getNumericGrade(grade))));
    }

}