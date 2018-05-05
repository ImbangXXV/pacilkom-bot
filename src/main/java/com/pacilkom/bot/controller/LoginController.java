package com.pacilkom.bot.controller;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.DatabaseController;
import com.pacilkom.csuilogin.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    private TelegramWebhookBot bot;

    @RequestMapping(value = "/csui-login", method = RequestMethod.GET)
    public String loginPage(@RequestParam("id") String enc_user_id, Model model) {
        model.addAttribute("id", enc_user_id);
        return "login-page";
    }

    @RequestMapping(value = "/csui-login/get-session", method = RequestMethod.POST)
    public String loginConfirm(@RequestParam("id") String enc_user_id,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password, Model model) {
        String access_token;
        int user_id = Integer.parseInt(Encryptor.decrypt(enc_user_id));
        try {
            model.addAttribute("id", user_id);
            access_token = CSUIAccount.getAccessToken(username, password);
            if (access_token != null) {
                DatabaseController.createSession(user_id, access_token);
                model.addAttribute("success", true);
                return "login-confirm";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("success", false);
        return "login-confirm";
    }

}
