package com.pacilkom.csui;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CSUILogin extends CSUIWebService {
    private static String API_URL = "https://akun.cs.ui.ac.id/oauth/token/";
    public static String getAccessToken(String username, String password) throws Exception {
        // Create map of parameters
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("grant_type", "password");

        // Parse JSON for access token
        String strResult = post(API_URL, params);
        JSONObject result = new JSONObject(strResult);
        return result.getString("access_token");
    }

    public static Map<String, Object> verifyAccessToken(String accessToken) throws Exception {
        // Create map of parameters
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("client_id", CSUI_CLIENT_ID);
        params.put("format", "json");

        // Parse JSON to Map<String, Object>
        String strResult = get(API_URL + "token/verify/", params);
        Map<String, Object> loginData = new JSONObject(strResult).toMap();
        loginData.put("access_token", accessToken);
        return loginData;
    }
}
