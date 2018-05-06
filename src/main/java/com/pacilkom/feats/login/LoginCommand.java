package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.Encryptor;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginCommand implements AuthBotCommand {
    public SendMessage execute(Long chatId, Integer userId) {
        SendMessage message;
        Map<String, Object> loginData = CSUIAccount.verifyLogin(userId);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        if (loginData != null) {
            message = new SendMessage(chatId,
                    "You have logged in via CSUI account as: " + loginData.get("username")
                            + " with role " + loginData.get("role"));
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
                    "You haven't logged in via CSUI account yet. Use this button below to "
                            + "unlock CSUI-exclusive features.");
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
