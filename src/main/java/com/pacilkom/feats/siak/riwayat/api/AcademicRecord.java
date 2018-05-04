package com.pacilkom.feats.siak.riwayat.api;

import com.pacilkom.feats.login.LoginVerifier;
import com.pacilkom.feats.siak.riwayat.comp.Transcript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class AcademicRecord {

    private static final String CLIENT_ID = "X3zNkFmepkdA47ASNMDZRX3Z9gqSU1Lwywu5WepG";
    private static final String API_LINK = "https://api.cs.ui.ac.id/siakngcs/mahasiswa/";

    public static List<Transcript> getAllTranscript(Integer userId) throws IOException {
        List<Transcript> transcripts = new LinkedList<>();
        JSONArray json = getJson(userId);
        for (int i = 0; i < json.length(); i++) {
            JSONObject obj = json.getJSONObject(i);
            transcripts.add(Transcript.convertJson(obj));
        }
        return transcripts;
    }

    private static JSONArray getJson(Integer userId) throws IOException {
        Map<String, Object> data = LoginVerifier.getData(userId);
        String result = "";
        URL url = new URL(API_LINK + data.get("identity_number")
        + "/riwayat/?access_token=" + data.get("access_token")
        + "&client_id=" + CLIENT_ID);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        while ((line = rd.readLine()) != null) {
            result += line;
        }

        rd.close();
        return new JSONArray(result);
    }

}
