package com.pacilkom.feats.siak.record.obj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GradeMapper {

    private Map<String, Double> gradeMap;
    private final static GradeMapper instance = new GradeMapper();
    private Set<String> passedGrade;

    private GradeMapper() {
        gradeMap = new HashMap<>();
        passedGrade = new HashSet<>();
        gradeMap.put("A",4.0);
        passedGrade.add("A");
        gradeMap.put("A-",3.7);
        passedGrade.add("A-");
        gradeMap.put("B+",3.3);
        passedGrade.add("B+");
        gradeMap.put("B",3.0);
        passedGrade.add("B");
        gradeMap.put("B-",2.7);
        passedGrade.add("B-");
        gradeMap.put("C+",2.3);
        passedGrade.add("C+");
        gradeMap.put("C",2.0);
        passedGrade.add("C");
        gradeMap.put("D",1.0);
        gradeMap.put("E",0.0);
        gradeMap.put("N",-1.0);

    };

    public static double getNumericGrade(String grade) {
        return instance.gradeMap.get(grade);
    }

    public static boolean gradePassed(String grade) {
        return instance.passedGrade.contains(grade);
    }
}
