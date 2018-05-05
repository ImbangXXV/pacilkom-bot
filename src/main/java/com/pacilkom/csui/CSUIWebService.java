package com.pacilkom.csui;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSUIWebService {
    public static final String CSUI_CLIENT_ID = System.getenv("CSUI_CLIENT_ID");
    private static final String API_URL =  "https://akun.cs.ui.ac.id/oauth/token/";

    public static String post(String url, Map<String, String> params) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(API_URL);

        // Request parameters and other properties
        List<NameValuePair> postParams = new ArrayList<>();
        for (String k : params.keySet()) {
            postParams.add(new BasicNameValuePair(k, params.get(k)));
        }

        // Header for POST request on CSUI API
        String auth = "Basic WDN6TmtGbWVwa2RBNDdBU05NRFpSWDNaOWdxU1UxTHd5d3U1V2Vw"
                + "RzpCRVFXQW43RDl6a2k3NEZ0bkNpWVhIRk50Ymg3eXlNWmFuNnlvMU1uaUdSVW"
                + "NGWnhkQnBobUU5TUxuVHZiTTEzM1dsUnBwTHJoTXBkYktqTjBxcU9OaHlTNGl2"
                + "Z0doczB0OVhlQ3M0Ym1JeUJLMldwbnZYTXE4VU5yTEFEMDNZeA==";

        httppost.addHeader("authorization", auth);
        httppost.addHeader("cache-control", "no-cache");
        httppost.setEntity(new UrlEncodedFormEntity(postParams, "UTF-8"));

        // Execute and get the response
        HttpResponse response = httpclient.execute(httppost);
        InputStream resultStream = response.getEntity().getContent();
        return IOUtils.toString(resultStream, StandardCharsets.UTF_8);
    }

    public static String get(String url, Map<String, String> params) throws IOException {
        // Parse parameters to the URL
        url += "?";
        List<String> paramsList = new ArrayList<>();
        for (String k : params.keySet()) {
            paramsList.add(k + "=" + params.get(k));
        }
        url += String.join("&", paramsList);

        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

        // Execute and get the response
        conn.setRequestMethod("GET");
        InputStream resultStream = conn.getInputStream();
        return IOUtils.toString(resultStream, StandardCharsets.UTF_8);
    }

}
