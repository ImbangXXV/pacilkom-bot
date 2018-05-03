package com.pacilkom.feats.siak.riwayat.api;

import com.pacilkom.feats.login.LoginVerifier;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ProgramInfo {


    private static final String CLIENT_ID = "X3zNkFmepkdA47ASNMDZRX3Z9gqSU1Lwywu5WepG";

    private static JSONObject getJson(Integer userId) throws Exception {
        Map<String, Object> data = LoginVerifier.getData(userId);
        URL url = new URL("https://api.cs.ui.ac.id/siakngcs/mahasiswa/" +
                "cari-info-program/" + data.get("identity_number") + "/?access_token="
                + data.get("access_token") + "&client_id=" + CLIENT_ID + "&format=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String result = "";

        String line;
        while ((line = rd.readLine()) != null) {
            result += line;
        }

        rd.close();
        JSONObject json = new JSONObject(result);
        return json;
    }
    public static int getFirstYear(Integer userId) {
        try {
            return getJson(userId).getInt("tahun_masuk");
        } catch (Exception e) {
            return -1;
        }

    }
}
