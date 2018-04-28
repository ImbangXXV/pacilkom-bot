package com.pacilkom.bot;

import com.pacilkom.feats.BotCommand;
import com.pacilkom.feats.ParamBotCommand;
import com.pacilkom.feats.example.HelloCommand;
import com.pacilkom.feats.login.LoginCommand;
import com.pacilkom.feats.scele.latestNews.SceleNewsCommand;
import com.pacilkom.feats.scele.latestTime.SceleTimeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateHandler {

	private Logger LOG = LoggerFactory.getLogger(UpdateHandler.class);

	private Map<String, BotCommand> commandMap;
	private Map<String, ParamBotCommand> paramCommandMap;

	public UpdateHandler() {
	    registerCommands();
	    registerParamCommands();
    }

	public BotApiMethod<? extends Serializable> handleUpdate(Update update) throws Exception {

		Message message = update.getMessage();

		Long chatId = message.getChatId();
		String text = message.getText().trim();

		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);

		int indexOf = text.indexOf(" ");

		if (indexOf > -1) {
		    String commandString = text.substring(0, indexOf);
			String queryString = text.substring(indexOf+1);
			if (paramCommandMap.containsKey(commandString)) {
                return paramCommandMap.get(commandString).execute(chatId, queryString);
            }
		} else if (commandMap.containsKey(text)) {
		    return commandMap.get(text).execute(chatId);
        } else if (text.equals("/login")) {
			String userId = message.getFrom().getId().toString();
			return LoginCommand.getInstance().execute(chatId, userId);
		}
        return null;
	}

	private void registerCommands() {
	    commandMap = new HashMap<>();
	    commandMap.put("/news", new SceleNewsCommand());
	    commandMap.put("/time", new SceleTimeCommand());
    }

    private void registerParamCommands() {
	    paramCommandMap = new HashMap<>();
	    paramCommandMap.put("/hello", new HelloCommand());
    }
}
