package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUIAccount;
import com.pacilkom.csuilogin.DatabaseController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class LoginVerifierTest {

    String accessToken;

    @Before
    public void setUp() throws Exception {
        accessToken = CSUIAccount.getAccessToken("muhammad.imbang", "aliceinwonderland25");
        // Create session with valid accessToken
        DatabaseController.createSession(-1,accessToken);
        // Create session with invalid accessToken
        DatabaseController.createSession(-2, "12345");
    }

    @Test
    public void verifyUserIdThatAlreadyExistsWithValidAccessToken() throws Exception{
        Map<String, Object> loginData = CSUIAccount.verifyLogin(-1);
        assertEquals(loginData.get("access_token"), accessToken);
        assertEquals(loginData.get("role"), "mahasiswa");
        assertEquals(loginData.get("identity_number"), "1606889502");
        assertEquals(loginData.get("username"), "muhammad.imbang");
    }

    @Test
    public void verifyUserIdThatAlreadyExistsWithInvalidAccessToken() throws Exception{
        assertNull(CSUIAccount.verifyLogin(-2));
    }

    @Test
    public void verifyUserIdThatIsNotExists() throws Exception{
        assertNull(CSUIAccount.verifyLogin(-3));
    }

    @After
    public void cleanUp() throws SQLException {
        DatabaseController.deleteSession(-1);
        DatabaseController.deleteSession(-2);
    }
}
