 package de.simonscholz.bot.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

 @Configuration
public class BotConfig {
 	@Autowired
	TelegramBotsApi api;

	@Bean
	public TelegramBotsApi api() {
		return new TelegramBotsApi();
	}

	@Bean
	public TelegramWebhookBot telegramBot() {
		TelegramWebhookBot bot = new PacilkomBot();

		try{
			api.registerBot(bot);
		}catch(TelegramApiException e){
			e.printStackTrace();
		}

		return bot;
	}

}
