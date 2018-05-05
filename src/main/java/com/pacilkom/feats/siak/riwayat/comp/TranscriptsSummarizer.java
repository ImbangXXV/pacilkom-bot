package com.pacilkom.feats.siak.riwayat.comp;

import java.util.List;
import java.util.stream.Collectors;

public class TranscriptsSummarizer {

    private final List<Transcript> transcripts;

    public TranscriptsSummarizer(List<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    public String summarize() {
        String summary = "Course(s) taken (credit unit(s)) :\n";

        summary += transcripts.stream().map(t ->
                String.format("%s (%d)",t.getSubject(), t.getCredit()))
                .collect(Collectors.joining("\n"));

        int totalSks = transcripts.stream()
                .mapToInt(Transcript::getCredit).sum();

        double totalScore = transcripts.stream()
                .filter(t -> !t.getGrade().equals("N") && t.getCredit() > 0)
                .mapToDouble(t -> GradeMapper
                        .getNumericGrade(t.getGrade())*t.getCredit())
                .sum();

        summary += "\n\nTotal Credit : " + totalSks
                + String.format("\nYour IP : %.2f", (totalScore / totalSks));

        return summary;
    }

}