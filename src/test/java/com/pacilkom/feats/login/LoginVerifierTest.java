package com.pacilkom.feats.login;

import com.pacilkom.csui.CSUILogin;
import com.pacilkom.csuilogin.DatabaseController;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoginVerifierTest {

    String accessToken;

    @Before
    public void setUp() throws Exception {
        accessToken = CSUILogin.getAccessToken("muhammad.imbang", "aliceinwonderland25");
        // Create session with valid accessToken
        DatabaseController.createSession(-1,accessToken);
        // Create session with invalid accessToken
        DatabaseController.createSession(-2, "12345");
    }

    @Test
    public void verifyUserIdThatAlreadyExistsWithValidAccessToken() throws Exception{
        Map<String, Object> loginData = LoginVerifier.getData(-1);
        assertEquals(loginData.get("access_token"), accessToken);
        assertEquals(loginData.get("role"), "mahasiswa");
        assertEquals(loginData.get("identity_number"), "1606889502");
        assertEquals(loginData.get("username"), "muhammad.imbang");
    }

    @Test
    public void verifyUserIdThatAlreadyExistsWithInvalidAccessToken() throws Exception{
        assertNull(LoginVerifier.getData(-2));
    }

    @Test
    public void verifyUserIdThatIsNotExists() throws Exception{
        assertNull(LoginVerifier.getData(-3));
    }

    @After
    public void cleanUp() throws SQLException {
        DatabaseController.deleteSession(-1);
        DatabaseController.deleteSession(-2);
    }
}
