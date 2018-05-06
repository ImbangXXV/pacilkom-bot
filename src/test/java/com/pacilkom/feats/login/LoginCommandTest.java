package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.DatabaseController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoginCommandTest {

    LoginCommand testCommand;

    @Before
    public void setUp() throws Exception{
        String accessToken = CSUIAccount.getAccessToken("muhammad.imbang", "aliceinwonderland25");
        DatabaseController.createSession(-1, accessToken);
        DatabaseController.createSession(-2, "1234");
        testCommand = new LoginCommand();
    }

    @Test
    public void botReplyWhenUserStillInSession() {
        SendMessage message = testCommand.execute((long) 1,-1);
        assertEquals(message.getText(), "Kamu sudah login dengan akun CSUI: muhammad.imbang dengan role mahasiswa");
    }

    @Test
    public void botReplyWhenUserNotInSession() {
        SendMessage message = testCommand.execute((long) 1, -2);
        assertEquals(message.getText(),
                "Kamu belum login dengan akun CSUI. Silahkan login melalui tombol login berikut!");
    }

    @After
    public void cleanUp() throws SQLException {
        DatabaseController.deleteSession(-1);
        DatabaseController.deleteSession(-2);
    }
}
