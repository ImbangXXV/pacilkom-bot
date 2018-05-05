package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.DatabaseController;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Map;

public class LogoutCommand implements AuthBotCommand {
    public SendMessage execute(Long chatId, Integer userId) {
        Map<String, Object> loginData = CSUIAccount.verifyLogin(userId);

        if (loginData!=null) {
            try {
                DatabaseController.deleteSession(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SendMessage(chatId, "Kamu sudah berhasil logout dari akun CSUI");
        } else {
            return new SendMessage(chatId, "Kamu tidak dalam keadaan login akun CSUI");
        }
    }
}
