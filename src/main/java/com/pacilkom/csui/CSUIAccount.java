package com.pacilkom.csui;

import com.pacilkom.bot.PacilkomBot;
import com.pacilkom.csuilogin.DatabaseController;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSUIAccount {
    private static final String API_URL =  "https://akun.cs.ui.ac.id/oauth/token/";

    public static String getAccessToken(String username, String password) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(API_URL);

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("grant_type", "password"));

        String auth = "Basic WDN6TmtGbWVwa2RBNDdBU05NRFpSWDNaOWdxU1UxTHd5d3U1V2Vw"
                + "RzpCRVFXQW43RDl6a2k3NEZ0bkNpWVhIRk50Ymg3eXlNWmFuNnlvMU1uaUdSVW"
                + "NGWnhkQnBobUU5TUxuVHZiTTEzM1dsUnBwTHJoTXBkYktqTjBxcU9OaHlTNGl2"
                + "Z0doczB0OVhlQ3M0Ym1JeUJLMldwbnZYTXE4VU5yTEFEMDNZeA==";

        httppost.addHeader("authorization", auth);
        httppost.addHeader("cache-control", "no-cache");
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        String access_token = null;
        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                String strResult = IOUtils.toString(instream, StandardCharsets.UTF_8);
                JSONObject result = new JSONObject(strResult);
                access_token = result.getString("access_token");
            } finally {
                instream.close();
            }
        }

        return access_token;
    }

    public static Map<String, Object> verifyAccessToken(String accessToken) {
        String result = "";
        try {
            URL url = new URL(API_URL+"/verify/?access_token="
                    + accessToken + "&client_id=" + PacilkomBot.CSUI_CLIENT_ID + "&format=json");
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
