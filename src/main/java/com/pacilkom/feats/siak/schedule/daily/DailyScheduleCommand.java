package com.pacilkom.feats.siak.schedule.daily;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import com.pacilkom.feats.interfaces.AuthEditableBotCommand;
import com.pacilkom.feats.siak.schedule.api.ScheduleAPI;
import com.pacilkom.feats.siak.schedule.objects.DaySchedule;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.*;

public class DailyScheduleCommand implements AuthEditableBotCommand, AuthBotCommand {

    private static final String ERROR_MESSAGE = "Daily schedule command's usage is:\n" +
            "/dailyschedule [year] [term] [day]";

    /**
     * AuthBotCommand interface's execute method. It returns the AuthEditableBotCommand execute method
     * but with null on messageId and text.
     * @param chatId = current user's chat ID
     * @param userId = current user's integer user ID
     * @return SendMessage or EditMessageText or DeleteMessage response;
     * @throws Exception
     */
    @Override
    public BotApiMethod<? extends Serializable> execute(Long chatId, Integer userId) throws Exception {
        return execute(chatId, userId, null, null);
    }

    /**
     * AuthEditableBotCoomand interface's execute method. This is the main execute method.
     * @param chatId = current user's chat ID
     * @param userId = current user's integer user ID
     * @param messageId = message
     * @param text = parameters text
     * @return SendMessage or EditMessageText or DeleteMessage response;
     */
    @Override
    public BotApiMethod<? extends Serializable> execute(Long chatId, Integer userId,
                                                        Integer messageId, String text) {
        // Check login fist
        Map<String, Object> loginData = CSUIAccount.verifyLogin(userId);
        if (loginData == null) {
            return new SendMessage(chatId, "Please login first to CSUI account " +
                    "using /login command");
        }

        // Parse the text, then make a map of parameters
        Map<String, String> params = parseParameter(text);
        params.put("chat_id", chatId.toString());
        params.put("message_id", messageId == null ? null : messageId.toString());
        params.put("access_token", (String) loginData.get("access_token"));
        params.put("npm", (String) loginData.get("identity_number"));

        // Determine the menu
        if (params.size() == 7) {
            // Get schedule from Fasilkom API
            DaySchedule schedule = ScheduleAPI.getApiResponse(params.get("access_token"), params.get("npm"),
                    params.get("year"), params.get("term"), params.get("day"));

            // Check for API response failure, if success, then convert DaySchedule to String
            if (schedule == null) {
                return new SendMessage(params.get("chat_id"), "I'm sorry, there are some weird " +
                        "connection issues so I can't connect to Fasilkom UI API server :( " +
                        "Please try again.");
            } else {
                return getDayResponse(params, schedule);
            }
        } else if (params.size() == 6) {
            return getTermResponse(params);
        } else if (params.size() == 5) {
            return getYearResponse(params);
        } else if (params.size() == 4) {
            return getUniversalResponse(params);
        }

        return new SendMessage(chatId, ERROR_MESSAGE);
    }

    /**
     * Method to parse parameters from message text.
     * @param text = message text
     * @return Map of String key and String value
     */
    public Map<String, String> parseParameter(String text) {
        Map<String, String> result = new HashMap<>();

        // If the text is empty (for AuthEditableBotCommand) or null (for AuthBotCommand)
        if (text == null || text.isEmpty()) {
            return result;
        }

        // Add new key value pair
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

    /**
     * The first menu which contains button of years or "current term".
     * @param params = map that contains mandatory variables from text
     * @return SendMessage or EditMessageText
     */
    public BotApiMethod<? extends Serializable> getUniversalResponse(Map<String, String> params) {
        // Add intro message and initiate inline keyboard
        String message = "Hi! To see your class schedule, choose the year first.. " +
                "(or \"Current Term\" if you want to show your current term schedule.";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        // Initialize response method
        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        // Check user's first year in college, based on first two digits of their ID number (NPM)
        int firstYear = Integer.parseInt("20" + params.get("npm").substring(0, 2));

        // Check current month and year
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        // Make academic year buttons
        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        for (int i = firstYear; ((month < 8 && i < year) || (month >= 8 && i <= year))
                && i <= firstYear + 6; i++) {
            if ((i - firstYear) / 2 > 0 && (i = firstYear) % 2 == 0) {
                row = new ArrayList<>();
                buttons.getKeyboard().add(row);
            }

            row.add(new InlineKeyboardButton().setText(i + "")
                    .setCallbackData("/dailyschedule " + i));
        }

        // Make button for current term and I'm Done
        row = new ArrayList<>();
        buttons.getKeyboard().add(row);

        String currentTerm = "/dailyschedule ";
        if (month < 6) {
            currentTerm += (year - 1) + " 2";
        } else if (month < 8) {
            currentTerm += (year - 1) + " 3";
        } else {
            currentTerm += year + " 1";
        }

        row.add(new InlineKeyboardButton().setText("Current Term").setCallbackData(currentTerm));
        row.add(new InlineKeyboardButton().setText("<< I'm Done!").setCallbackData("banish"));

        return response;
    }

    /**
     * The first menu which contains button of terms.
     * @param params = map that contains mandatory variables from text
     * @return SendMessage or EditMessageText
     */
    public BotApiMethod<? extends Serializable> getYearResponse(Map<String, String> params) {
        // Add intro message and initiate inline keyboard
        String message = "Okay... you choose academic year of " + params.get("year") +
                ". Then you should choose the term now (1 = odd, 2 = even)";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        // Initialize response method
        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        // Make button for every term
        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("1")
                .setCallbackData("/dailyschedule " + params.get("year") + " 1"));
        row.add(new InlineKeyboardButton().setText("2")
                .setCallbackData("/dailyschedule " + params.get("year") + " 2"));
        row.add(new InlineKeyboardButton().setText("SP")
                .setCallbackData("/dailyschedule " + params.get("year") + " 3"));
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/dailyschedule "));

        return response;
    }

    /**
     * The first menu which contains button of days.
     * @param params = map that contains mandatory variables from text
     * @return SendMessage or EditMessageText
     */
    public BotApiMethod<? extends Serializable> getTermResponse(Map<String, String> params) {
        // Add intro message and initiate inline keyboard
        String message = "Okay... you choose academic year of " + params.get("year") + " and term "
                + params.get("term") + ". Then you should choose the day now.";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        // Initialize response method
        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        // Initialize lists of keyboard buttons
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> curr;
        buttons.getKeyboard().add(row1);
        buttons.getKeyboard().add(row2);
        buttons.getKeyboard().add(row3);

        // Make button for every days
        Object[] days = ScheduleAPI.EN_ID_DAYS.keySet().toArray();
        for (int i = 0; i < days.length; i++) {
            if (i % 2 == 0) {
                curr = row1;
            } else {
                curr = row2;
            }

            curr.add(new InlineKeyboardButton()
                    .setText((String) days[i]).setCallbackData("/dailyschedule "
                            + params.get("year") + " " + params.get("term") + " " + days[i]));
        }

        row3.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/dailyschedule " + params.get("year")));

        return response;
    }

    /**
     * The first menu which contains schedule information for selected day, term, year, and NPM.
     * @param params = map that contains mandatory variables from text
     * @return SendMessage or EditMessageText
     */
    public BotApiMethod<? extends Serializable> getDayResponse(Map<String, String> params,
                                                               DaySchedule schedule) {
        // Add intro message and initiate inline keyboard
        String message = "I get all the information I need.. Here are your schedule for "
                + params.get("day") + " on academic year " + params.get("year") + " term "
                + params.get("term") + ":\n";
        InlineKeyboardMarkup buttons = createKeyboardInstance();
        message += schedule.toString();

        /// Initialize response method
        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        // Make button to go back or I'm Done (destruct message)
        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/dailyschedule " + params.get("year")
                        + " " + params.get("term")));
        row.add(new InlineKeyboardButton().setText("<< I'm Done!").setCallbackData("banish"));

        return response;
    }

    /**
     * Method to create response method instance.
     * @param params = map that contains mandatory variables from text
     * @param message = text message that will be embedded in the response message
     * @param keyboard = inline key
     * @return SendMessage or EditMessageText
     */
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

    /**
     * Method to create new empty inline keyboard.
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createKeyboardInstance() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        return markupInline.setKeyboard(rowsInline);
    }
}