package com.pacilkom.feats.siak.schedule.daily;

import com.pacilkom.csuilogin.SessionDatabase;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import com.pacilkom.feats.interfaces.AuthEditableBotCommand;
import com.pacilkom.feats.login.LoginVerifier;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static com.pacilkom.feats.login.LoginVerifier.CLIENT_ID;

public class DailyScheduleCommand implements AuthEditableBotCommand, AuthBotCommand {
    private static final String ERROR_MESSAGE = "Daily schedule command's usage is:\n" +
            "/dailyschedule [year] [term] [day]";

    @Override
    public BotApiMethod<? extends Serializable> execute(Long chatId, Integer userId) throws Exception {
        return execute(chatId, userId, null, null);
    }

    @Override
    public BotApiMethod<? extends Serializable> execute(Long chatId, Integer userId,
                                                        Integer messageId, String text) {
        Map<String, Object> loginData = LoginVerifier.getData(userId);
        if (loginData == null) {
            return new SendMessage(chatId, "Please login first to CSUI account " +
                    "using /login command");
        }

        Map<String, String> params = parseParameter(text);
        params.put("chat_id", chatId.toString());
        params.put("message_id", messageId == null ? null : messageId.toString());
        params.put("access_token", (String) loginData.get("access_token"));
        params.put("npm", (String) loginData.get("identity_number"));

        if (params.size() == 7) {
            return getDayResponse(params);
        } else if (params.size() == 6) {
            return getTermResponse(params);
        } else if (params.size() == 5) {
            return getYearResponse(params);
        } else if (params.size() == 4) {
            return getUniversalResponse(params);
        }

        return new SendMessage(chatId, ERROR_MESSAGE);
    }

    private Map<String, String> parseParameter(String text) {
        Map<String, String> result = new HashMap<>();

        if (text == null) {
            return result;
        }

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
        }

        return result;
    }

    private BotApiMethod<? extends Serializable> getUniversalResponse(Map<String, String> params) {
        String message = "Hi! To see your class schedule, chosse the year first.. " +
                "(or \"Current Term\" if you want to show your current term schedule.";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        int firstYear = getFirstYear(params.get("access_token"), params.get("npm"));
        if (firstYear < 0) {
            return new SendMessage(params.get("chat_id"), "I'm sorry, there are some weird " +
                    "connection issues so I can't connect to Fasilkom UI API server :( " +
                    "Please try again.");
        }

        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        for (int i = firstYear; (month < 8 && i < year) || (month >= 8 && i <= year); i++) {
            if ((i - firstYear) / 2 > 0 && (i = firstYear) % 2 == 0) {
                row = new ArrayList<>();
                buttons.getKeyboard().add(row);
            }

            row.add(new InlineKeyboardButton()
                        .setText(i + "").setCallbackData("/dailyschedule " + i));
        }

        return response;
    }

    private BotApiMethod<? extends Serializable> getYearResponse(Map<String, String> params) {


        BotApiMethod<? extends Serializable> result;
        if (params.get("message_id") == null) {
            result = new SendMessage();
        } else {
            result = new EditMessageText();
        }

        return result;
    }

    private BotApiMethod<? extends Serializable> getTermResponse(Map<String, String> params) {


        BotApiMethod<? extends Serializable> result;
        if (params.get("message_id") == null) {
            result = new SendMessage();
        } else {
            result = new EditMessageText();
        }

        return result;
    }

    private BotApiMethod<? extends Serializable> getDayResponse(Map<String, String> params) {


        BotApiMethod<? extends Serializable> result = null;
        return result;
    }

    private BotApiMethod<? extends Serializable> createMethodInstance
            (Map<String, String> params, String message, InlineKeyboardMarkup keyboard) {
        String chatId = params.get("chat_id");
        String messageId = params.get("message_id");

        if (messageId == null) {
            return new SendMessage(chatId, message).setReplyMarkup(keyboard);
        } else {
            return new EditMessageText().setChatId(chatId).setText(message)
                    .setMessageId(Integer.parseInt(messageId)).setReplyMarkup(keyboard);
        }
    }

    private InlineKeyboardMarkup createKeyboardInstance() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        return markupInline.setKeyboard(rowsInline);
    }

    private int getFirstYear(String accessToken, String npm) {
        try {
            URL url = new URL("https://api.cs.ui.ac.id/siakngcs/mahasiswa/" +
                    "cari-info-program/" + npm + "/?access_token=" + accessToken + "&client_id=" +
                    CLIENT_ID + "&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";

            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
            rd.close();
            JSONObject json = new JSONObject(result);
            System.out.println(result);
            return json.getInt("tahun_masuk");
        } catch (Exception e) {
            return -1;
        }

    }
}