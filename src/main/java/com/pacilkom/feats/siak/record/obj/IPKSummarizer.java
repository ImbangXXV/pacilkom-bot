package com.pacilkom.feats.siak.record.obj;

import java.util.List;

public class IPKSummarizer extends TranscriptsSummarizer {

    public IPKSummarizer(List<Transcript> transcripts) {
        super(transcripts);
    }

    public String summarize() {
        String summary = "Course(s) taken (credit unit(s)) :\n";

        int totalSks = transcripts.stream()
                .filter(t ->GradeMapper.gradePassed(t.getGrade()))
                .mapToInt(Transcript::getCredit).sum();

        double totalScore = transcripts.stream()
                .filter(t -> GradeMapper.gradePassed(t.getGrade())
                        && t.getCredit() > 0).mapToDouble(t -> GradeMapper
                        .getNumericGrade(t.getGrade())*t.getCredit())
                        .sum();

        summary += "\n\nTotal Credit : " + totalSks
                + String.format("\nYour IPK : %.2f", (totalScore / totalSks));

        return summary;
    }
}
