package com.pacilkom.csui;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSUIAcademic extends CSUIWebService {
    private static final String API_URL = "https://api.cs.ui.ac.id/siakngcs/mahasiswa/";

    public static JSONObject getBasicAcademicData(Integer userId) throws IOException {
        Map<String, Object> data = CSUIAccount.verifyLogin(userId);
        String resp = get(API_URL + "cari-info-program/" + data.get("identity_number"),
                createParamsMap((String) data.get("access_token")));
        return new JSONObject(resp);
    }

    public static JSONArray getAcademicTranscript(int userId) throws IOException {
            Map<String, Object> data = CSUIAccount.verifyLogin(userId);
            String resp = get(API_URL + data.get("identity_number") + "/riwayat/",
                    createParamsMap((String) data.get("access_token")));
            return new JSONArray(resp);
    }

    public static JSONArray getScheduleByDay(String accessToken, String npm, String year,
                                             String term, String day) throws IOException {
        String resp = get(API_URL + "jadwal-list/" + year + "/" + term + "/" + day + "/" + npm,
                createParamsMap(accessToken));
        return new JSONArray(resp);
    }

    private static Map<String, String> createParamsMap(String accessToken) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("client_id", CSUI_CLIENT_ID);
        params.put("format", "json");
        return params;
    }
}
