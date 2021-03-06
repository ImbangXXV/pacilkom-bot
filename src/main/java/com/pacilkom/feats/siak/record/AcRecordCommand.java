package com.pacilkom.feats.siak.record;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.feats.interfaces.AuthBotCommand;
import com.pacilkom.feats.interfaces.AuthEditableBotCommand;
import com.pacilkom.feats.siak.record.api.AcademicRecord;
import com.pacilkom.feats.siak.record.api.ProgramInfo;
import com.pacilkom.feats.siak.record.obj.IPKSummarizer;
import com.pacilkom.feats.siak.record.obj.Summarizable;
import com.pacilkom.feats.siak.record.obj.Transcript;
import com.pacilkom.feats.siak.record.obj.TranscriptsSummarizer;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        Map<String, Object> loginData = CSUIAccount.verifyLogin(userId);
        if (loginData == null) {
            return new SendMessage(chatId, "Please login first to CSUI account " +
                    "using /login command");
        }

        Map<String, String> params = parseParameter(text);
        params.put("chat_id", chatId.toString());
        params.put("message_id", messageId == null ? null : messageId.toString());
        params.put("user_id", String.valueOf(userId));

        switch (params.size()) {
            case 6:
                if (params.get("id").equals("sum")) {
                    return summarizeIp(params);
                }
                return inform(params);
            case 5:
                return courseResponse(params);
            case 4:
                if (params.get("year").equals("ipk")) {
                    return summarizeIpk(params);
                }
                return termResponse(params);
            case 3:
                return yearResponse(params);
        }

        return new SendMessage(chatId, ERROR_MESSAGE);
    }

    public Map<String, String> parseParameter(String text) {
        Map<String, String> result = new HashMap<>();

        if (text == null || text.isEmpty()) {
            return result;
        }

        String[] params = text.split(" ");
        String[] keys = {"year", "term", "id"};

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

    public BotApiMethod<? extends Serializable> yearResponse(Map<String, String> params) {
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

        int year = Calendar.getInstance().get(Calendar.YEAR);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        for (int i = firstYear; i < year; i++) {
            if ((i - firstYear) / 2 > 0 && (i = firstYear) % 2 == 0) {
                row = new ArrayList<>();
                buttons.getKeyboard().add(row);
            }

            row.add(new InlineKeyboardButton().setText(i + "")
                    .setCallbackData("/record " + i));
        }

        row = new ArrayList<>();
        buttons.getKeyboard().add(row);

        row.add(new InlineKeyboardButton()
                .setText("Summarize My IPK")
                .setCallbackData("/record ipk"));

        row = new ArrayList<>();
        buttons.getKeyboard().add(row);

        row.add(new InlineKeyboardButton()
                .setText("<< I'm Done!")
                .setCallbackData("banish"));

        return response;
    }

    public BotApiMethod<? extends Serializable> termResponse(Map<String, String> params) {
        String message = "Now that you chose the year of " + params.get("year")
                + ", you should choose which term now (1 = odd, 2 = even, 3 = short)";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("1")
                .setCallbackData("/record " + params.get("year") + " 1"));

        int month = Calendar.getInstance().get(Calendar.MONTH);
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        int termYear = Integer.parseInt(params.get("year"));

        if (termYear < currYear - 1 || month > 7) {
            row.add(new InlineKeyboardButton().setText("2")
                    .setCallbackData("/record " + params.get("year") + " 2"));
        }
        if (termYear < currYear - 1 || month > 9) {
            row.add(new InlineKeyboardButton().setText("3")
                    .setCallbackData("/record " + params.get("year") + " 3"));
        }
        row.add(new InlineKeyboardButton().setText("<< Back")
                .setCallbackData("/record "));
        return response;
    }

    public BotApiMethod<? extends Serializable> courseResponse(Map<String,
            String> params) throws IOException {
        int year = Integer.parseInt(params.get("year"));
        int term = Integer.parseInt(params.get("term"));
        String message = "";

        InlineKeyboardMarkup buttons = createKeyboardInstance();

        List<Transcript> transcripts = AcademicRecord
                .getAllTranscript(Integer.parseInt(params.get("user_id")))
                .stream().filter(t -> t.getTerm() == term && t.getYear() == year)
                .collect(Collectors.toList());

        List<InlineKeyboardButton> row = null;
        if (transcripts.size() > 0) {
            message += "Kay, you chose the year of " + params.get("year")
                    + " term " + params.get("term") + ". Now, you can either choose "
                    + " the specific course you'd like to see or just summarize the entire"
                    + " semester by choosing Summarize button.";
            for (Transcript script : transcripts) {
                row = new ArrayList<>();
                row.add(new InlineKeyboardButton().setText(script.getSubject())
                        .setCallbackData("/record " + params.get("year")
                                + " " + params.get("term") + " " + script.getId()));
                buttons.getKeyboard().add(row);
            }
            row = new ArrayList<>();
            row.add(new InlineKeyboardButton().setText("Summarize Semester")
                    .setCallbackData("/record " + params.get("year")
                            + " " + params.get("term") + " sum"));
            buttons.getKeyboard().add(row);

        } else {
            message += "\nIt seems you have no record on academic year " + year
                    + " term " + term + "...";
        }
        row = new ArrayList<>();
        row.add(new InlineKeyboardButton().setText("<< Back")
                .setCallbackData("/record " + params.get("year")));
        row.add(new InlineKeyboardButton()
                .setText("<< I'm Done!").setCallbackData("banish"));
        buttons.getKeyboard().add(row);

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        return response;
    }


    public BotApiMethod<? extends Serializable> inform(Map<String,
            String> params) throws IOException {
        int courseId = Integer.parseInt(params.get("id"));
        int userId = Integer.parseInt(params.get("user_id"));
        Transcript transcript = AcademicRecord.getAllTranscript(userId)
                .stream().filter(t -> t.getId() == courseId).findAny().get();

        String message = transcript.summarize();

        InlineKeyboardMarkup buttons = createKeyboardInstance();

        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/record " + params.get("year")
                        + " " + params.get("term")));
        row.add(new InlineKeyboardButton()
                .setText("<< I'm Done!").setCallbackData("banish"));

        return response;

    }

    public BotApiMethod<? extends Serializable> summarizeIp(Map<String,
            String> params) throws IOException {
        int year = Integer.parseInt(params.get("year"));
        int term = Integer.parseInt(params.get("term"));
        String message = "Alright, your academic record on academic year "
                + params.get("year") + " term " + params.get("term") + ":\n\n";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        List<Transcript> transcripts = AcademicRecord
                .getAllTranscript(Integer.parseInt(params.get("user_id")))
                .stream().filter(t -> t.getTerm() == term && t.getYear() == year)
                .collect(Collectors.toList());

        TranscriptsSummarizer summarizer = new TranscriptsSummarizer(transcripts);
        message += summarizer.summarize();

        message += "\n\nPlease note that some of the subjects may not be included"
                + " due to incomplete information.";


        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/record " + params.get("year") + " " + params.get("term")));
        row.add(new InlineKeyboardButton().setText("<< I'm Done!").setCallbackData("banish"));

        return response;

    }

    public BotApiMethod<? extends Serializable> summarizeIpk(Map<String,
            String> params) throws IOException {
        String message = "Hoho... this is your academic record so far\n\n";
        InlineKeyboardMarkup buttons = createKeyboardInstance();

        List<Transcript> transcripts = AcademicRecord
                .getAllTranscript(Integer.parseInt(params.get("user_id")));

        Summarizable summarizer = new IPKSummarizer(transcripts);
        message += summarizer.summarize();

        message += "\n\nPlease note that some of the subjects may not be included"
                + " due to incomplete information.";


        BotApiMethod<? extends Serializable> response = createMethodInstance(params,
                message, buttons);

        List<InlineKeyboardButton> row = new ArrayList<>();
        buttons.getKeyboard().add(row);
        row.add(new InlineKeyboardButton().setText("<< Go Back")
                .setCallbackData("/record "));
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