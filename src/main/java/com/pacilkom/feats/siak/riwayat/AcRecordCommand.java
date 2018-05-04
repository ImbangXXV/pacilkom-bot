package com.pacilkom.feats.siak.riwayat;

import com.pacilkom.feats.interfaces.AuthBotCommand;
import com.pacilkom.feats.interfaces.AuthEditableBotCommand;
import com.pacilkom.feats.login.LoginVerifier;
import com.pacilkom.feats.siak.riwayat.api.AcademicRecord;
import com.pacilkom.feats.siak.riwayat.api.ProgramInfo;
import com.pacilkom.feats.siak.riwayat.comp.GradeMapper;
import com.pacilkom.feats.siak.riwayat.comp.Transcript;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AcRecordCommand implements AuthBotCommand, AuthEditableBotCommand {

    private static final String ERROR_MESSAGE = "Daily schedule command's usage is:\n"
            + "/record [year] [term]";


    @Override
    public BotApiMethod<? extends Serializable> execute(Long chatId,
                                                        Integer userId) throws Exception {
        return execute(chatId, userId, null, null);
    }

    @Override
    public BotApiMethod<? extends Serializable> execute(Long chatId,
                                                        Integer userId, Integer messageId,
                                                        String text) throws Exception {
        Map<String, Object> loginData = LoginVerifier.getData(userId);
        if (loginData == null) {
            return new SendMessage(chatId, "Please login first to CSUI account " +
                    "using /login command");
        }

        Map<String, String> params = parseParameter(text);
        params.put("chat_id", chatId.toString());
        params.put("message_id", messageId == null ? null : messageId.toString());
        params.put("user_id", String.valueOf(userId));

        switch(params.size()) {
            case 5:
                return summarize(params);
            case 4:
                return termResponse(params);
            case 3:
                return yearResponse(params);
        }

        return new SendMessage(chatId, ERROR_MESSAGE);
    }

    private Map<String, String> parseParameter(String text) {
        Map<String, String> result = new HashMap<>();

        if (text == null || text.isEmpty()) {
            return result;
        }

        String[] params = text.split(" ");
        String[] keys = {"year", "term"};

        switch (params.length) {
            case 2:
                result.put(keys[1], params[1]);
            case 1:
                result.put(keys[0], params[0]);
                break;
        }

        return result;
    }

    private BotApiMethod<? extends Serializable> yearResponse(Map<String, String> params) {
        String message = "Alright, Please choose which year of academic"
                + " record you'd like to see.. ";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        int firstYear = ProgramInfo.getFirstYear(Integer.parseInt(params.get("user_id")));
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
                    .setCallbackData("/record " + i));
        }

        row = new ArrayList<>();
        buttons.getKeyboard().add(row);

        row.add(new InlineKeyboardButton().setText("<< I'm Done!").setCallbackData("banish"));

        return response;
    }

    private BotApiMethod<? extends Serializable> termResponse(Map<String, String> params) {
        String message = "Now that you chose the year of " + params.get("year") +
                ", you should choose which term now (1 = odd, 2 = even)";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("1")
                .setCallbackData("/record " + params.get("year") + " 1"));
        row.add(new InlineKeyboardButton().setText("2")
                .setCallbackData("/record " + params.get("year") + " 2"));
        row.add(new InlineKeyboardButton().setText("<< Back")
                .setCallbackData("/record "));

        return response;
    }

    private BotApiMethod<? extends Serializable> summarize(Map<String,
            String> params) throws IOException, SQLException {
        int year = Integer.parseInt(params.get("year"));
        int term = Integer.parseInt(params.get("term"));
        String message = "All right, your academic record on academic year "
                + params.get("year") + " term " + params.get("term") + ":\n\n";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        List<Transcript> transcripts = AcademicRecord
                .getAllTranscript(Integer.parseInt(params.get("user_id")))
                .stream().filter(t -> t.getTerm() == term && t.getYear() == year)
                .collect(Collectors.toList());

        if (transcripts.size() > 0) {
            message += transcripts.stream().map(t -> t.toString())
                    .collect(Collectors.joining("\n\n"));
            int totalSks = transcripts.stream()
                    .mapToInt(Transcript::getCredit).sum();
            double totalScore = transcripts.stream()
                    .filter(t -> !t.getGrade().equals("N") && t.getCredit() > 0)
                    .mapToDouble(t -> GradeMapper.getNumericGrade(t.getGrade()))
                    .sum();
            message += "Total SKS : " + totalSks
                    + "\nYour IP : " + totalScore
                    + "\nPlease note that some of the subjects are not included"
                    + " due to incomplete informations.";

        } else {
            message += "\nIt seems you have no record on academic year " + year
            + " term " + term + "...";
        }


        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/record " + params.get("year")));
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
        List<List<InlineKeyboardButton>> rowsInline = new LinkedList<>();
        return markupInline.setKeyboard(rowsInline);
    }

}
