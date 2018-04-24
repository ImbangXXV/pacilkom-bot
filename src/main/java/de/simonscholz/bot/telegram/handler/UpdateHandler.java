package de.simonscholz.bot.telegram.handler;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;

public interface UpdateHandler {
    BotApiMethod<? extends Serializable> handleUpdate(Update update);
}
