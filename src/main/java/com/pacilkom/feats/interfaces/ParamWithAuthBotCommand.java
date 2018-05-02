package com.pacilkom.feats.interfaces;

import java.io.Serializable;
import org.telegram.telegrambots.api.methods.BotApiMethod;

public interface ParamWithAuthBotCommand {
    BotApiMethod<? extends Serializable> execute(Long chatId, Integer userId, String text) throws Exception;
}
