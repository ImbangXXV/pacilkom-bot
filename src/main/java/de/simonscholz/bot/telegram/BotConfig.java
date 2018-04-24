 package de.simonscholz.bot.telegram;

import org.springframework.context.annotation.Bean;

import com.pengrad.telegrambot.TelegramBot;

import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class BotConfig {
	private static final String BASE_URL = "http://www.dmi.dk/Data4DmiDk/";

	@Bean
	public Retrofit retrofit() {
		return new Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(BASE_URL).build();
	}

	@Bean
	public TelegramBot telegramBot(BotProperties botProperties) {
		return new TelegramBot(botProperties.getApiKey());
	}

}
