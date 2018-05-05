package com.pacilkom.feats.siak.riwayat.comp;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TranscriptsSummarizerTest {

    private Transcript sample1;
    private String course1 = "AB";
    private int credit1 = 2;
    private String grade1 = "A";
    private Transcript sample2;
    private String course2 = "CD";
    private int credit2 = 2;
    private String grade2 = "B";
    private List<Transcript> transcripts;
    private TranscriptsSummarizer summarizer;

    @Before
    public void setUp() {
        sample1 = new Transcript(1,course1,"Mr. Y",
                credit1, 2017, 1, grade1);
        sample2 = new Transcript(2, course2, "Mrs. Z",
                credit2, 2017, 2, grade2);
        transcripts = new LinkedList<>();
        transcripts.add(sample1);
        transcripts.add(sample2);
        summarizer = new TranscriptsSummarizer(transcripts);
    }

    @Test
    public void testSummarizeMethodWorkingProperly() {
        String summary = summarizer.summarize();

        assertTrue(summary.contains(course1));
        assertTrue(summary.contains("(" + credit1 + ")"));
        assertTrue(summary.contains(course2));
        assertTrue(summary.contains("(" + credit2 + ")"));
        assertTrue(summary.contains(String
                .valueOf(3.5)));
        assertTrue(summary.contains(String.valueOf(4)));
    }

}