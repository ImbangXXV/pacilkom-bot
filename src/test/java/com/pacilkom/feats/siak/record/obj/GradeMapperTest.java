package com.pacilkom.feats.siak.record.obj;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeMapperTest {

    private String sampleGrade = "A";
    private int expectedVal = 4;

    @Test
    public void testMapperMapProperly() {
        double numValue = GradeMapper.getNumericGrade(sampleGrade);
        assertEquals(expectedVal, numValue);
    }

}