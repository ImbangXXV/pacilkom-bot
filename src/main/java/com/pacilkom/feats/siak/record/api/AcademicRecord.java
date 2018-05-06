package com.pacilkom.feats.siak.record.api;

import com.pacilkom.csui.CSUIAcademic;
import com.pacilkom.feats.siak.record.obj.Transcript;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class AcademicRecord {
    public static List<Transcript> getAllTranscript(Integer userId) throws IOException {
        List<Transcript> transcripts = new LinkedList<>();
        JSONArray json = CSUIAcademic.getAcademicTranscript(userId);
        for (int i = 0; i < json.length(); i++) {
            JSONObject obj = json.getJSONObject(i);
            if (!obj.isNull("kelas") &&
                    !obj.getString("nilai").equals("N")) {
                transcripts.add(Transcript.convertJson(obj));
            }
        }
        return transcripts;
    }

}
