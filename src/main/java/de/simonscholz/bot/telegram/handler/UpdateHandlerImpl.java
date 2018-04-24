package de.simonscholz.bot.telegram.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.Serializable;

@Component
public class UpdateHandlerImpl implements UpdateHandler {

	private Logger LOG = LoggerFactory.getLogger(UpdateHandlerImpl.class);

	@Autowired
	private TelegramWebhookBot telegramBot;

	@Override
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
                SendMessage sendHello = new SendMessage(chatId, "Hello, this is your message: " + queryString);
                return sendHello;
            }
		}

        return null;
	}
}
