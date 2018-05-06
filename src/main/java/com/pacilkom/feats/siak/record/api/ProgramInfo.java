package com.pacilkom.feats.siak.record.api;

import com.pacilkom.csui.CSUIAcademic;

public class ProgramInfo {
    public static int getFirstYear(Integer userId) {
        try {
            return CSUIAcademic.getBasicAcademicData(userId).getInt("tahun_masuk");
        } catch (Exception e) {
            return -1;
        }
    }
}
