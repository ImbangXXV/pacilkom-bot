package com.pacilkom.feats.siak.riwayat.comp;

import java.util.HashMap;
import java.util.Map;

public class GradeMapper {

    private Map<String, Double> gradeMap;
    private final static GradeMapper instance = new GradeMapper();

    private GradeMapper() {
        gradeMap = new HashMap<>();
        gradeMap.put("A",4.0);
        gradeMap.put("A-",3.7);
        gradeMap.put("B+",3.3);
        gradeMap.put("B",3.0);
        gradeMap.put("B-",2.7);
        gradeMap.put("C+",2.3);
        gradeMap.put("C",2.0);
        gradeMap.put("D",1.0);
        gradeMap.put("E",0.0);
        gradeMap.put("N",-1.0);

    };

    public static double getNumericGrade(String grade) {
        return instance.gradeMap.get(grade);
    }
}
