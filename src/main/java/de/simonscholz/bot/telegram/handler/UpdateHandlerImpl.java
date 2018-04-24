package de.simonscholz.bot.telegram.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

@Component
public class UpdateHandlerImpl implements UpdateHandler {

	private Logger LOG = LoggerFactory.getLogger(UpdateHandlerImpl.class);

	@Autowired
	private TelegramBot telegramBot;

	@Override
	public void handleUpdate(Update update) {
		Message message = update.message();

		Long chatId = message.chat().id();
		String text = message.text();

		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);

		int indexOf = text.indexOf(" ");

		if (indexOf > -1) {
			String queryString = text.substring(indexOf+1);

			if (text.startsWith("/hello")) {
                SendMessage sendHello = new SendMessage(chatId, "Hello, this is your message: " + queryString);
                telegramBot.execute(sendHello);
            }

		}

	}
}
