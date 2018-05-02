package com.pacilkom.feats.interfaces;

import java.io.Serializable;
import org.telegram.telegrambots.api.methods.BotApiMethod;

public interface AuthEditableBotCommand {
    BotApiMethod<? extends Serializable> execute(Long chatId, Integer userId,
                                                 Integer messageId, String text) throws Exception;
}
