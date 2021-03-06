package com.pacilkom.feats.siak.schedule.daily;

import com.pacilkom.bot.PacilkomBot;
import com.pacilkom.feats.siak.schedule.objects.DaySchedule;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class DailyScheduleCommandTest {
    DailyScheduleCommand instance = new DailyScheduleCommand();

    @Test
    public void noParameterTextReturnsUniversalResponse() throws Exception {
        BotApiMethod<? extends Serializable> response = instance.execute((long) -1, -1);
        assertTrue(response instanceof SendMessage);
    }

    @Test
    public void hasMessageIdReturnsEditMessageText() {
        // use verify because can't mock API response
        Map<String, String> params = getDummyParams((long) -1, -1, "2014", "14");
        BotApiMethod<? extends Serializable> response = instance.getYearResponse(params);
        assertTrue(response instanceof EditMessageText);

        params = getDummyParams((long) -1, -1, "2014 1", "14");
        response = instance.getTermResponse(params);
        assertTrue(response instanceof EditMessageText);

        params = getDummyParams((long) -1, -1, "2014 1 Monday", "14");
        DaySchedule schedule = new DaySchedule("Monday", "1", "2014",
                "1499999999", new ArrayList<>());
        response = instance.getDayResponse(params, schedule);
        assertTrue(response instanceof EditMessageText);
    }

    @Test
    public void noMessageIdReturnsSendMessage() {
        BotApiMethod<? extends Serializable> response = instance.execute((long) -1, -1, null, "");
        assertTrue(response instanceof SendMessage);
        response = instance.execute((long) -1, -1, null, "2017");
        assertTrue(response instanceof SendMessage);
        response = instance.execute((long) -1, -1, null, "2017 1");
        assertTrue(response instanceof SendMessage);
        response = instance.execute((long) -1, -1, null, "2017 1 Monday");
        assertTrue(response instanceof SendMessage);
    }

    @Test
    public void checkUniversalResponseIsCorrect() {
        Map<String, String> params = getDummyParams((long) 439700050, null, "", "17");
        SendMessage oriResponse = (SendMessage) instance.getUniversalResponse(params);
        assertEquals(oriResponse.getText(), "Hi! To see your class schedule, choose the year " +
                "first.. (or \"Current Term\" if you want to show your current term schedule.");

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) oriResponse.getReplyMarkup();
        InlineKeyboardButton firstKey = markup.getKeyboard().get(0).get(0);
        assertEquals("2017", firstKey.getText());
        assertEquals("/dailyschedule 2017", firstKey.getCallbackData());
    }

    @Test
    public void checkYearResponseIsCorrect() {
        Map<String, String> params = getDummyParams((long) -1, null, "2017", "17");
        SendMessage oriResponse = (SendMessage) instance.getYearResponse(params);
        assertEquals("Okay... you choose academic year of 2017. Then you should choose "
                + "the term now (1 = odd, 2 = even)", oriResponse.getText());

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) oriResponse.getReplyMarkup();
        InlineKeyboardButton firstKey = markup.getKeyboard().get(0).get(0);
        assertEquals(firstKey.getText(), "1");
        assertEquals(firstKey.getCallbackData(), "/dailyschedule 2017 1");
    }

    @Test
    public void checkTermResponseIsCorrect() {
        Map<String, String> params = getDummyParams((long) -1, null, "2017 1", "17");
        SendMessage oriResponse = (SendMessage) instance.getTermResponse(params);
        assertEquals(oriResponse.getText(), "Okay... you choose academic year of 2017 and term 1. "
                + "Then you should choose the day now.");

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) oriResponse.getReplyMarkup();
        InlineKeyboardButton firstKey = markup.getKeyboard().get(0).get(0);
        assertEquals(firstKey.getText(), "Monday");
        assertEquals(firstKey.getCallbackData(), "/dailyschedule 2017 1 Monday");
    }

    @Test
    public void checkDayResponseIsCorrect() {
        Map<String, String> params = getDummyParams((long) -1, null, "2017 1 Monday", "17");
        DaySchedule schedule = new DaySchedule("Monday", "1", "2017",
                "179999999", new ArrayList<>());

        SendMessage oriResponse = (SendMessage) instance.getDayResponse(params, schedule);
        assertTrue(oriResponse.getText().contains("I get all the information I need.. "
                + "Here are your schedule for Monday on academic year 2017 term 1:\n"), oriResponse.getText());

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) oriResponse.getReplyMarkup();
        InlineKeyboardButton firstKey = markup.getKeyboard().get(0).get(0);
        assertEquals(firstKey.getText(), "<< Go Back");
        assertEquals(firstKey.getCallbackData(), "/dailyschedule 2017 1");
    }

    private Map<String, String> getDummyParams(Long chatId, Integer messageId,
                                               String text, String year) {
        Map<String, String> params = instance.parseParameter(text);
        params.put("chat_id", chatId.toString());
        params.put("message_id", messageId == null ? null : messageId.toString());
        params.put("access_token", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        params.put("npm", year + "99999999");
        return params;
    }
}