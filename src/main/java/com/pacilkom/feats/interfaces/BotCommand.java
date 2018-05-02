package com.pacilkom.feats.interfaces;

import java.io.Serializable;
import org.telegram.telegrambots.api.methods.BotApiMethod;

public interface BotCommand {
    BotApiMethod<? extends Serializable> execute(Long chatId);
}
