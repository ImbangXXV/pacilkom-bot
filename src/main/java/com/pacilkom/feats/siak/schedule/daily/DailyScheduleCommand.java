package com.pacilkom.feats.siak.schedule.daily;

import com.pacilkom.feats.interfaces.AuthEditableBotCommand;
import com.pacilkom.feats.login.LoginVerifier;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DailyScheduleCommand implements AuthEditableBotCommand {
    private static final String ERROR_MESSAGE = "Daily schedule command's usage is:\n" +
            "/dailyschedule [year] [term] [day]";

    @Override
    public BotApiMethod<? extends Serializable> execute(Long chatId, Integer userId,
                                                        Integer messageId, String text) {
        Map<String, Object> loginData = LoginVerifier.getData(userId);
        if (loginData == null) {
            return new SendMessage(chatId, "Please login first to CSUI account using /login command");
        }

        Map<String, String> params = parseParameter(text);

        if (params == null) {
            return getUniversalResponse();
        } else if (params.size() == 3) {
            return getDayResponse(params);
        } else if (params.size() == 2) {
            return getTermResponse(params);
        } else if (params.size() == 1) {
            return getYearResponse(params);
        }

        return new SendMessage(chatId, ERROR_MESSAGE);
    }

    private Map<String, String> parseParameter(String text) {
        Map<String, String> result = new HashMap<>();
        String[] params = text.split(" ");
        String[] keys = {"year", "term", "day"};

        switch (params.length) {
            case 3:
                result.put(keys[2], params[2]);
            case 2:
                result.put(keys[1], params[1]);
            case 1:
                result.put(keys[0], params[0]);
                break;
            default:
                return null;
        }

        return result;
    }

    private SendMessage getUniversalResponse(){
        SendMessage result = new SendMessage();
        return result;
    }

    private SendMessage getYearResponse(Map<String, String> params){
        SendMessage result = new SendMessage();
        return result;
    }

    private SendMessage getTermResponse(Map<String, String> params) {
        SendMessage result = new SendMessage();
        return result;
    }

    private SendMessage getDayResponse(Map<String, String> params) {
        SendMessage result = new SendMessage();
        return result;
    }

}