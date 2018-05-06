package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.DatabaseController;
import com.pacilkom.csuilogin.Encryptor;
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
        String username = Encryptor.decrypt("cf437be0b0085201b64a01ebdc102196");
        String password = Encryptor.decrypt("6b6157a375f9a5593198e835497617173421b904a8b3"
                + "10541601ab14f8d1ce38");
        String accessToken = CSUIAccount.getAccessToken(username, password);
        DatabaseController.createSession(-1, accessToken);
        DatabaseController.createSession(-2, "1234");
        testCommand = new LoginCommand();
    }

    @Test
    public void botReplyWhenUserStillInSession() {
        SendMessage message = testCommand.execute((long) 1,-1);
        assertEquals(message.getText(), "You have logged in via CSUI account as: muhammad.imbang with role mahasiswa");
    }

    @Test
    public void botReplyWhenUserNotInSession() {
        SendMessage message = testCommand.execute((long) 1, -2);
        assertEquals(message.getText(),
                "You haven't logged in via CSUI account yet. Use this button below to "
                                                    + "unlock CSUI-exclusive features.");
    }

    @After
    public void cleanUp() throws SQLException {
        DatabaseController.deleteSession(-1);
        DatabaseController.deleteSession(-2);
    }
}
