package com.pacilkom.feats.siak.schedule.daily;

import com.pacilkom.csuilogin.SessionDatabase;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import com.pacilkom.feats.interfaces.AuthEditableBotCommand;
import com.pacilkom.feats.login.LoginVerifier;
import org.json.JSONArray;
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
    private static final Map<String, Object> EN_ID_DAYS = new JSONObject("{\"Monday\":\"Senin\","
            + "\"Tuesday\":\"Selasa\",\"Wednesday\":\"Rabu\",\"Thursday\":\"Kamis\","
            + "\"Friday\":\"Jumat\",\"Saturday\":\"Sabtu\"}").toMap();

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

        if (text == null || text.isEmpty()) {
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
        String message = "Hi! To see your class schedule, choose the year first.. " +
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

            row.add(new InlineKeyboardButton().setText(i + "")
                    .setCallbackData("/dailyschedule " + i));
        }

        row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("<< I'm Done!").setCallbackData("banish"));

        return response;
    }

    private BotApiMethod<? extends Serializable> getYearResponse(Map<String, String> params) {
        String message = "Okay... you choose academic year of " + params.get("year") +
                ". Then you should choose the term now (1 = odd, 2 = even)";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("1")
                .setCallbackData("/dailyschedule " + params.get("year") + " 1"));
        row.add(new InlineKeyboardButton().setText("2")
                .setCallbackData("/dailyschedule " + params.get("year") + " 2"));
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/dailyschedule "));

        return response;
    }

    private BotApiMethod<? extends Serializable> getTermResponse(Map<String, String> params) {
        String message = "Okay... you choose academic year of " + params.get("year") + " and term "
                + params.get("term") + ". Then you should choose the day now.";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        buttons.getKeyboard().add(row1);
        buttons.getKeyboard().add(row2);
        buttons.getKeyboard().add(row3);

        row1.add(new InlineKeyboardButton()
                .setText("Monday").setCallbackData("/dailyschedule " + params.get("year") + " "
                        + params.get("term") + " Monday"));
        row2.add(new InlineKeyboardButton()
                .setText("Tuesday").setCallbackData("/dailyschedule " + params.get("year") + " "
                        + params.get("term") + " Tuesday"));
        row1.add(new InlineKeyboardButton()
                .setText("Wednesday").setCallbackData("/dailyschedule " + params.get("year") + " "
                        + params.get("term") + " Wednesday"));
        row2.add(new InlineKeyboardButton()
                .setText("Thursday").setCallbackData("/dailyschedule " + params.get("year") + " "
                        + params.get("term") + " Thursday"));
        row1.add(new InlineKeyboardButton()
                .setText("Friday").setCallbackData("/dailyschedule " + params.get("year") + " "
                        + params.get("term") + " Friday"));
        row2.add(new InlineKeyboardButton()
                .setText("Saturday").setCallbackData("/dailyschedule " + params.get("year") + " "
                        + params.get("term") + " Saturday"));
        row3.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/dailyschedule " + params.get("year")));

        return response;
    }

    private BotApiMethod<? extends Serializable> getDayResponse(Map<String, String> params) {
        String message = "I get all the information I need.. Here are your schedule for "
                + params.get("day") + " on academic year " + params.get("year") + " term "
                + params.get("term") + ":\n";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        try {
            URL url = new URL("https://api.cs.ui.ac.id/siakngcs/jadwal-list/" + params.get("year")
                    + "/" + params.get("term") + "/" + translateDay(params.get("day"))
                    + "/" + params.get("npm") + "/?access_token=" + params.get("access_token")
                    + "&client_id=" + CLIENT_ID + "&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";

            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }

            rd.close();
            List<Object> json = new JSONArray(result).toList();

            if (json.size() > 0) {
                for (Object obj : json) {
                    Map<String, Object> item = (Map<String, Object>) obj;
                    String mulai = (String) item.get("jam_mulai");
                    String selesai = (String) item.get("jam_selesai");
                    mulai.substring(0, mulai.length() - 3);
                    selesai.substring(0, selesai.length() - 3);

                    Map<String, Object> room = (Map<String, Object>) item.get("id_ruang");
                    Map<String, Object> detail = (Map<String, Object>) item.get("kd_kls_sc");
                    List<Object> lecturers = (List<Object>) detail.get("pengajar");

                    message += "\n" + mulai + " - " + selesai + " -> " + (String) detail.get("nm_kls")
                            + " at " + room.get("nm_ruang") + " with ";

                    for (int i = 0; i < lecturers.size(); i++) {
                        Map<String, Object> lecturer = (Map<String, Object>) lecturers.get(i);
                        message += (i > 0 ? " and " : "") + lecturer.get("nama");
                    }
                }
            } else {
                message += "\nHmm... there are no class that you must attend on that day...";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SendMessage(params.get("chat_id"), "I'm sorry, there are some weird " +
                    "connection issues so I can't connect to Fasilkom UI API server :( " +
                    "Please try again.");
        }

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/dailyschedule " + params.get("year")
                        + " " + params.get("term")));
        row.add(new InlineKeyboardButton().setText("<< I'm Done!").setCallbackData("banish"));

        return response;
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

    private String translateDay(String enDay) {
        return (String) EN_ID_DAYS.get(enDay);
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

            rd.close();
            JSONObject json = new JSONObject(result);
            return json.getInt("tahun_masuk");
        } catch (Exception e) {
            return -1;
        }

    }
}