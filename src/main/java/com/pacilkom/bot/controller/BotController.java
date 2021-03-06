package com.pacilkom.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.Serializable;

@RestController
public class BotController {

    @Autowired
    private TelegramWebhookBot bot;

    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    public void webhook(@RequestBody Update update) throws TelegramApiException {
        bot.execute(bot.onWebhookUpdateReceived(update));
    }
}
