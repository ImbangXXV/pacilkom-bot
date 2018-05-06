package com.pacilkom.csuilogin;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class DatabaseControllerTest {

    @Test
    public void controllerCRUDTest() throws Exception{
        // Check insert and read operation
        DatabaseController.createSession(999,"12345");
        DatabaseController.createSession(888, "678910");

        assertEquals(DatabaseController.getAccessToken(999), "12345");
        assertEquals(DatabaseController.getAccessToken(888), "678910");

        // Check update operation
        DatabaseController.createSession(999, "54321");
        DatabaseController.createSession(888, "109876");

        assertEquals(DatabaseController.getAccessToken(999), "54321");
        assertEquals(DatabaseController.getAccessToken(888), "109876");

        // Check delete operation
        DatabaseController.deleteSession(999);
        DatabaseController.deleteSession(888);

        assertNull(DatabaseController.getAccessToken(999));
        assertNull(DatabaseController.getAccessToken(888));

    }

}
