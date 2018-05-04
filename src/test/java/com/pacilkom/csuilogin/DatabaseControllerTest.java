package com.pacilkom.csuilogin;

import org.junit.Test;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SessionDatabaseTest {

    SessionDatabase database;

    @Before
    public void setUP() {
        database = SessionDatabase.getInstance();
    }

    @Test
    public void databseCRUDTest() throws Exception{
        // Check insert and read operation
        database.createSession(999,"12345");
        database.createSession(888, "678910");

        assertEquals(database.getAccessToken(999), "12345");
        assertEquals(database.getAccessToken(888), "678910");

        // Check update operation
        database.createSession(999, "54321");
        database.createSession(888, "109876");

        assertEquals(database.getAccessToken(999), "54321");
        assertEquals(database.getAccessToken(888), "109876");

        // Check delete operation
        database.deleteSession(999);
        database.deleteSession(888);

        assertNull(database.getAccessToken(999));
        assertNull(database.getAccessToken(888));

    }

}
