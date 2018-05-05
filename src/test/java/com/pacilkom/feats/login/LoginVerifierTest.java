package com.pacilkom.feats.login;

import com.pacilkom.bot.controller.LoginController;
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
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoginVerifierTest {

    String accessToken;

    @Before
    public void setUp() throws Exception {
        accessToken = getAccessToken("muhammad.imbang", "aliceinwonderland25");
        // Create session with valid accessToken
        DatabaseController.createSession(-1,accessToken);
        // Create session with invalid accessToken
        DatabaseController.createSession(-2, "12345");
    }

    @Test
    public void verifyUserIdThatAlreadyExistsWithValidAccessToken() throws Exception{
        Map<String, Object> loginData = LoginVerifier.getData(-1);
        assertEquals(loginData.get("access_token"), accessToken);
        assertEquals(loginData.get("role"), "mahasiswa");
        assertEquals(loginData.get("identity_number"), "1606889502");
        assertEquals(loginData.get("username"), "muhammad.imbang");
    }

    @Test
    public void verifyUserIdThatAlreadyExistsWithInvalidAccessToken() throws Exception{
        assertNull(LoginVerifier.getData(-2));
    }

    @Test
    public void verifyUserIdThatIsNotExists() throws Exception{
        assertNull(LoginVerifier.getData(-3));
    }

    private static String getAccessToken(String username, String password) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://akun.cs.ui.ac.id/oauth/token/");

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
}
