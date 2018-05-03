package com.pacilkom.feats.login;

import com.pacilkom.csuilogin.SessionDatabase;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class LoginVerifier {
    public static final String CLIENT_ID = "X3zNkFmepkdA47ASNMDZRX3Z9gqSU1Lwywu5WepG";

    public static Map<String, Object> getData(int userId) {
        String result = "";
        try {
            String accessToken = SessionDatabase.getInstance().getAccessToken(userId);
            URL url = new URL("https://akun.cs.ui.ac.id/oauth/token/verify/?access_token="
                    + accessToken + "&client_id=" + CLIENT_ID + "&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }

            rd.close();
            Map<String, Object> loginData = new JSONObject(result).toMap();
            loginData.put("access_token", accessToken);
            return loginData;
        } catch (Exception e) {
            return null;
        }
    }
}

