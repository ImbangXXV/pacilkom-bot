package com.pacilkom.csui;

import com.pacilkom.csuilogin.DatabaseController;
import com.pacilkom.feats.siak.riwayat.api.AcademicRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CSUIAcademic extends CSUIWebService {
    private static final String API_URL = "https://api.cs.ui.ac.id/siakngcs/mahasiswa/";

    public static JSONObject getBasicAcademicData(Integer userId) throws Exception {
        // Query from database
        Map<String, Object> data = CSUIAccount.verifyLogin(userId);

        // Create map of parameters
        Map<String, String> params = new HashMap<>();
        params.put("access_token", (String) data.get("access_token"));
        params.put("client_id", CSUI_CLIENT_ID);
        params.put("format", "json");
        params.put("grant_type", "password");

        // Parse JSON for basic academic data
        String resp = post(API_URL + "cari-info-program/" + data.get("identity_number"), params);
        return new JSONObject(resp);
    }

    public static JSONArray getAcademicTranscript(Integer userId) throws Exception {
        // Query from database
        Map<String, Object> data = CSUIAccount.verifyLogin(userId);

        // Create map of parameters
        Map<String, String> params = new HashMap<>();
        params.put("access_token", (String) data.get("access_token"));
        params.put("client_id", CSUI_CLIENT_ID);
        params.put("format", "json");
        params.put("grant_type", "password");

        // Parse JSON for academic transcript
        String resp = get(API_URL + data.get("identity_number") + "/riwayat/", params);
        return new JSONArray(resp);
    }
}
