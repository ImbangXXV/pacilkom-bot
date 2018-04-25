package com.pacilkom.bot;

import com.pacilkom.bot.commands.BasicCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

import java.io.Serializable;

@Component
public class UpdateHandler {

	private Logger LOG = LoggerFactory.getLogger(UpdateHandler.class);


	@Autowired
	private TelegramWebhookBot telegramBot;

    public UpdateHandler() {

    }

	public BotApiMethod<? extends Serializable> handleUpdate(Update update) {
		Message message = update.getMessage();

		Long chatId = message.getChatId();
		String text = message.getText();

		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);

		int indexOf = text.indexOf(" ");

		if (indexOf > -1) {
			String queryString = text.substring(indexOf+1);

			if (text.startsWith("/hello")) {
                return BasicCommands.getInstance().hello(chatId, queryString);
            } else if (text.startsWith("/help")) {
                return BasicCommands.getInstance().help(chatId);
            } else if (text.startsWith("/about")) {
                return BasicCommands.getInstance().about(chatId);
            } else if (text.startsWith("/")) {
			    // if received command is invalid
                SendMessage sendHello = new SendMessage(chatId, "Hmm");
                return sendHello;
            } else {
			    // if received no comand
                return processMessage(chatId,text);
            }
		}
        return null;
	}

    private BotApiMethod<? extends Serializable> processMessage(Long chatId, String text) {
        if (text.toLowerCase().contains("hello")) {

        }
	    return null;
    }
}
