package com.pacilkom.feats.login;

import com.pacilkom.csuilogin.SessionDatabase;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class LogoutCommand implements AuthBotCommand {
    public SendMessage execute(Long chatId, Integer userId) {
        String userName = LoginVerifier.verify(userId);
        if (userName != null) {
            try {
                SessionDatabase.getInstance().deleteSession(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SendMessage(chatId, "Kamu sudah berhasil logout dari akun CSUI");
        } else {
            return new SendMessage(chatId, "Kamu tidak dalam keadaan login akun CSUI");
        }
    }
}
