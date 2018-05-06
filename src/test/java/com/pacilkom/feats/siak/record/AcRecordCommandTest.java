package com.pacilkom.feats.siak.record;

import org.junit.Test;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AcRecordCommandTest {

    private AcRecordCommand instance = new AcRecordCommand();

    @Test
    public void noParameterTextReturnsSendMessage() throws Exception {
        BotApiMethod<? extends Serializable> response = instance.execute((long) -1, -1);
        assertTrue(response instanceof SendMessage);
    }

    @Test
    public void noMessageIdReturnsSendMessage() throws Exception {
        BotApiMethod<? extends Serializable> response =
                instance.execute((long) -1, -1, null, "");
        assertTrue(response instanceof SendMessage);
        response = instance.execute((long) -1, -1, null, "2016");
        assertTrue(response instanceof SendMessage);
        response = instance.execute((long) -1, -1, null, "2016 1");
        assertTrue(response instanceof SendMessage);
    }


    @Test
    public void dummyParamCantConnectYearResponse() {
        Map<String, String> params = getDummyParams((long) -1, null, "");
        SendMessage oriResponse = (SendMessage) instance.yearResponse(params);

        assertEquals("I'm sorry, there are some weird " +
                "connection issues so I can't connect to Fasilkom UI API server :( "
                + "Please try again.", oriResponse.getText());
    }

    @Test
    public void checkTermResponseIsCorrect() {
        Map<String, String> params = getDummyParams((long) -1, null, "2016");
        SendMessage oriResponse = (SendMessage) instance.termResponse(params);
        assertEquals("Now that you chose the year of " + params.get("year")
                + ", you should choose which term now (1 = odd, 2 = even, 3 = short)"
                , oriResponse.getText());

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) oriResponse.getReplyMarkup();
        InlineKeyboardButton firstKey = markup.getKeyboard().get(0).get(0);
        assertEquals(firstKey.getText(), "1");
        assertEquals(firstKey.getCallbackData(), "/record 2016 1");
    }

    @Test
    public void dummyParamCantBypassCourseResponse() throws IOException {
        Map<String, String> params = getDummyParams((long) -1, null, "2016 1");

        boolean catched = false;
        try {
            SendMessage oriResponse = (SendMessage) instance.courseResponse(params);
        } catch (Exception e) {
            catched = true;
        }

        assertTrue(catched);
    }

    private Map<String, String> getDummyParams
            (Long chatId, Integer messageId, String text) {
        Map<String, String> params = instance.parseParameter(text);
        params.put("chat_id", chatId.toString());
        params.put("message_id", messageId == null
                ? null : messageId.toString());
        params.put("user_id", "000100");
        return params;
    }


}