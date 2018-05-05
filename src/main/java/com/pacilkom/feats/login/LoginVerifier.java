package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUILogin;
import com.pacilkom.csuilogin.DatabaseController;

import java.util.Map;

public class LoginVerifier {

    public static Map<String, Object> getData(int userId) {
        String result = "";
        try {
            String accessToken = DatabaseController.getAccessToken(userId);
            return CSUILogin.verifyAccessToken(accessToken);
        } catch (Exception e) {
            return null;
        }
    }
}

