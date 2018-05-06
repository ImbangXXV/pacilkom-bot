package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.DatabaseController;

import java.util.Map;

public class LoginVerifier {

    public static Map<String, Object> getData(int userId) {
        String result = "";
        try {
            String accessToken = DatabaseController.getAccessToken(userId);
            return CSUIAccount.verifyLogin(userId);
        } catch (Exception e) {
            return null;
        }
    }
}

