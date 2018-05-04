package com.pacilkom.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

public class PacilkomBot extends TelegramWebhookBot {
    public static final String USERNAME = System.getenv("BOT_USERNAME");
    public static final String API_KEY = System.getenv("BOT_API_KEY_1") + ":"
            + System.getenv("BOT_API_KEY_2");
    public static final String PATH = System.getenv("BOT_WEBHOOK_PATH");
    public static final String CSUI_CLIENT_ID = System.getenv("CSUI_CLIENT_ID");
    public static final String DATABASE_URL = System.getenv("DATABASE_URL")
            + "?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

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
