package com.pacilkom.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

public class PacilkomBot extends TelegramWebhookBot {
    public static final String USERNAME = "pacilkom_bot";
    public static final String API_KEY   = "531440373:AAHKnxwbUIG0D1DlwDGxRqQIsoZ0efX0cVs";
    public static final String PATH = "https://pacilkom-bot.herokuapp.com/webhook";

    @Autowired
    private UpdateHandler handler;

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {
            try {
                return handler.handleUpdate(update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return API_KEY;
    }

    @Override
    public String getBotPath() {
        return PATH;
    }
}
