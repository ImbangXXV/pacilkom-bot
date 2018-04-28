package com.pacilkom.csuilogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.springframework.ui.Model;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Controller
@RestController
public class LoginController {

    @Autowired
    private TelegramWebhookBot bot;

    @GetMapping("/login")
    public String webhook(Model model) {
        return "login-page";
    }

    @RequestMapping(value = "/login/get-session", method = RequestMethod.POST)
    public void webhook(@RequestBody Update update) throws TelegramApiException {
        bot.execute(bot.onWebhookUpdateReceived(update));
    }
}
