package com.pacilkom.csuilogin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.springframework.ui.Model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private TelegramWebhookBot bot;

    @GetMapping("/login")
    public String loginPage(@RequestParam("id") int user_id, Model model) {
        return "login-page";
    }

    @PostMapping("/login/get-session")
    public void loginConfirm(@RequestParam("id") int user_id,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password) {
        String access_token = null;
        try {
            access_token = getAccessToken(username, password);
            if (access_token != null) {
                SessionDatabase.getInstance().createSession(user_id, access_token);
                // show success html "you can close this window"
            }
        } catch (Exception e) {
            e.printStackTrace();
            // redirect back to /login
        }
    }

    private String getAccessToken(String username, String password) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://akun.cs.ui.ac.id/oauth/token/");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

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
                JSONObject result = new JSONObject(instream.toString());
                access_token = result.getString("access_token");
            } finally {
                instream.close();
            }
        }

        return access_token;
    }
}
