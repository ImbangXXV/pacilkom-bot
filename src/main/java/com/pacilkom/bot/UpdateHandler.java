package com.pacilkom.bot;

import com.pacilkom.feats.example.AboutCommand;
import com.pacilkom.feats.example.HelpCommand;
import com.pacilkom.feats.example.StartCommand;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import com.pacilkom.feats.interfaces.BotCommand;
import com.pacilkom.feats.interfaces.ParamBotCommand;
import com.pacilkom.feats.interfaces.AuthEditableBotCommand;
import com.pacilkom.feats.example.HelloCommand;
import com.pacilkom.feats.login.LoginCommand;
import com.pacilkom.feats.login.LogoutCommand;
import com.pacilkom.feats.scele.latestNews.SceleNewsCommand;
import com.pacilkom.feats.scele.latestTime.SceleTimeCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateHandler {
	private Map<String, BotCommand> commandMap;
	private Map<String, AuthBotCommand> authCommandMap;
	private Map<String, ParamBotCommand> paramCommandMap;
	private Map<String, AuthEditableBotCommand> authEditableCommandMap;

	public UpdateHandler() {
	    registerCommands();
	    registerAuthCommands();
	    registerParamCommands();
	    registerauthEditableCommands();
    }

	public BotApiMethod<? extends Serializable> handleUpdate(Update update) {
		String text;
		Long chatId;
		Integer userId;
		Integer messageId;

		if (update.hasCallbackQuery()) {
            CallbackQuery message = update.getCallbackQuery();
            text = message.getData();
            chatId = message.getMessage().getChatId();
            userId = message.getFrom().getId();
            messageId = message.getMessage().getMessageId();
        } else {
            Message message = update.getMessage();
            text = message.getText().trim();
            chatId = message.getChatId();
            userId = message.getFrom().getId();
            messageId = message.getMessageId();
        }

		int indexOf = text.indexOf(" ");

		if (indexOf > -1) {
		    String commandString = text.substring(0, indexOf);
			String queryString = text.substring(indexOf+1);

			if (paramCommandMap.containsKey(commandString)) {
                return paramCommandMap.get(commandString).execute(chatId, queryString);
            } else if (authEditableCommandMap.containsKey(commandString)) {
				return authEditableCommandMap.get(commandString)
						.execute(chatId, userId, messageId, queryString);
			}
		} else if (commandMap.containsKey(text)) {
		    return commandMap.get(text).execute(chatId);
        } else if (authCommandMap.containsKey(text)) {
            return authCommandMap.get(text).execute(chatId, userId);
        } else if (text.substring(0,1).equals("/")) {
		    return new SendMessage(chatId, "Hello, I am Pacilkom Bot. Am I cute? " +
                    "I hope so... BTW, I don't really understand what you are saying. " +
                    "So please use bot commands. You can use /help for the command list.");
        }
        return new SendMessage(chatId, "Command not available. Use /help for more info.");
	}

	private void registerCommands() {
	    commandMap = new HashMap<>();
		commandMap.put("/start", new StartCommand());
		commandMap.put("/about", new AboutCommand());
		commandMap.put("/help", new HelpCommand());
	    commandMap.put("/news", new SceleNewsCommand());
	    commandMap.put("/time", new SceleTimeCommand());
    }

	private void registerAuthCommands() {
		authCommandMap = new HashMap<>();
		authCommandMap.put("/login", new LoginCommand());
		authCommandMap.put("/logout", new LogoutCommand());
	}

    private void registerParamCommands() {
	    paramCommandMap = new HashMap<>();
	    paramCommandMap.put("/hello", new HelloCommand());
    }

	private void registerauthEditableCommands() {
		authEditableCommandMap = new HashMap<>();
        paramCommandMap.put("/dailyschedule", new HelloCommand());
	}
}
