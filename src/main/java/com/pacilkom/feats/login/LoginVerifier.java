package com.pacilkom.feats.login;

import com.pacilkom.csuilogin.SessionDatabase;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginVerifier {
    private static final String CLIENT_ID = "X3zNkFmepkdA47ASNMDZRX3Z9gqSU1Lwywu5WepG";

    public static String verify(int userId) {
        try {
            String accessToken = SessionDatabase.getInstance().getAccessToken(userId);
            return getUserData(accessToken).getString("identity_number");
        } catch (Exception e) {
            return null;
        }
    }

    private static JSONObject getUserData(String accessToken) throws Exception {
        String result = "";
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
        return new JSONObject(result);
    }
}
