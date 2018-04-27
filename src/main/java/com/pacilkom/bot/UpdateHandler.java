package com.pacilkom.bot;

import com.pacilkom.feats.BotCommand;
import com.pacilkom.feats.scele.latestNews.SceleNewsCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateHandler {

	private Logger LOG = LoggerFactory.getLogger(UpdateHandler.class);

	@Autowired
	private TelegramWebhookBot telegramBot;

	private Map<String, BotCommand> commands;

	public UpdateHandler() {
	    commands = new HashMap<>();
	    commands.put("/news", new SceleNewsCommand());
    }

	public BotApiMethod<? extends Serializable> handleUpdate(Update update) throws IOException {
		Message message = update.getMessage();

		Long chatId = message.getChatId();
		String text = message.getText();

		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);

		int indexOf = text.indexOf(" ");

		if (indexOf > -1) {
		    String command = text.substring(0, indexOf);
			String queryString = text.substring(indexOf+1);

			if (text.startsWith("/hello")) {
                SendMessage sendHello = new SendMessage(chatId, "Hello, this is your message: " + queryString);
                return sendHello;
            } else if (commands.containsKey(command)) {
			    return commands.get(command).execute(chatId);
            }
		}

        return null;
	}
}
