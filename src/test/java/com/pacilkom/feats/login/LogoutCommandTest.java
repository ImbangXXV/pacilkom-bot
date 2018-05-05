package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.DatabaseController;
import org.junit.Test;
import org.junit.Before;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LogoutCommandTest {

    LogoutCommand testCommand;
    String accessToken;

    @Before
    public void setUp() throws Exception {
        accessToken = CSUIAccount.getAccessToken("muhammad.imbang", "aliceinwonderland25");
        DatabaseController.createSession(999, accessToken);
        testCommand = new LogoutCommand();
    }

    @Test
    public void botReplyWhenUserIsLogged() throws Exception{
        SendMessage message = testCommand.execute((long) 1,999);
        assertEquals("Kamu sudah berhasil logout dari akun CSUI", message.getText());
    }

    @Test
    public void botReplyWhenUserIsNotLogged() {
        SendMessage message = testCommand.execute((long) 2, 70);
        assertEquals("Kamu tidak dalam keadaan login akun CSUI", message.getText());
    }
}
