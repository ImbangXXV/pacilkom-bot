package de.simonscholz.bot.telegram;

import de.simonscholz.bot.telegram.handler.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

public class PacilkomBot extends TelegramWebhookBot {
    private static final String USERNAME = "pacilkom-bot";
    private static final String API_KEY_ORI = "531440373:AAHKnxwbUIG0D1DlwDGxRqQIsoZ0efX0cVs";
    private static final String PATH = "https://pacilkom-bot.herokuapp.com/webhook";

    @Autowired
    private UpdateHandler handler;

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return handler.handleUpdate(update);
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return API_KEY_ORI;
    }

    @Override
    public String getBotPath() {
        return PATH;
    }
}
