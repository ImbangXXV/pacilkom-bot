package com.pacilkom.feats.login;

import com.pacilkom.feats.interfaces.AuthBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements AuthBotCommand {
    public SendMessage execute(Long chatId, Integer userId) {
        String userName = LoginVerifier.verify(userId);
        SendMessage message;
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        if (userName != null) {
            message = new SendMessage(chatId,
                    "Kamu sudah login dengan akun CSUI: " + userName);
            // Add inline keyboard button(s)
            rowInline.add(new InlineKeyboardButton()
                    .setText("Switch Account")
                    .setUrl("https://pacilkom-bot.herokuapp.com/csui-login/?id=" +
                            Encryptor.encrypt(userId.toString())));
            rowInline.add(new InlineKeyboardButton()
                    .setText("Logout")
                    .setCallbackData("/logout"));

        } else {
            message = new SendMessage(chatId,
                    "Kamu belum login dengan akun CSUI. Silahkan login melalui tombol "
                            + "login berikut!");
            // Add inline keyboard button(s)
            rowInline.add(new InlineKeyboardButton()
                    .setText("Login with CSUI account")
                    .setUrl("https://pacilkom-bot.herokuapp.com/csui-login/?id=" +
                            Encryptor.encrypt(userId.toString())));
        }

        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }
}
