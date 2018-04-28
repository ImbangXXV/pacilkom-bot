package com.pacilkom.feats.login;

import com.pacilkom.feats.ParamBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements ParamBotCommand {
    private static LoginCommand instance = null;

    private LoginCommand() {}

    public static LoginCommand getInstance() {
        if(instance == null) {
            instance = new LoginCommand();
        }
        return instance;
    }

    public SendMessage execute(Long chatId, String text) {
        int userId = Integer.parseInt(text);
        String userName = LoginVerifier.verify(userId);
        if (userName != null) {
            return new SendMessage(chatId,
                    "Kamu sudah login dengan akun CSUI: " + userName);
        } else {
            SendMessage message = new SendMessage(chatId,
                    "Kamu belum login dengan akun CSUI. Silahkan login melalui tombol "
                            + "login berikut!");
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton()
                    .setText("Login with CSUI account")
                    .setUrl("https://pacilkom-bot.herokuapp.com/csui-login/?user_id=" + userId));
            // Set the keyboard to the markup
            rowsInline.add(rowInline);
            // Add it to the message
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);
            return message;
        }
    }
}
