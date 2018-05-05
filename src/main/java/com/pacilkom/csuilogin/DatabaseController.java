package com.pacilkom.csuilogin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {
    private static Connection c = SessionDatabase.getInstance().getConnection();

    public static void createSession(int user_id, String access_token) throws SQLException {
        Statement stmt = c.createStatement();
        String query;
        if (getAccessToken(user_id) == null) {
            // use INSERT
            query = "INSERT INTO BOT_USER (user_id, access_token)\n"
                    + "VALUES (" + user_id + ", '" + access_token + "');";
        } else {
            // use UPDATE
            query = "UPDATE BOT_USER SET access_token = '" + access_token + "'\n"
                    + "WHERE user_id = " + user_id + ";";
        }

        stmt.executeUpdate(query);
        stmt.close();
    }

    public static void deleteSession(int user_id) throws SQLException {
        Statement stmt = c.createStatement();
        String query = "DELETE FROM BOT_USER WHERE user_id = " + user_id + ";";
        stmt.executeUpdate(query);
        stmt.close();
    }

    public static String getAccessToken(int user_id) throws SQLException {
        Statement stmt = c.createStatement();
        String query = "SELECT access_token\n"
                + "FROM BOT_USER\n"
                + "WHERE user_id = " + user_id + ";";

        ResultSet rs = stmt.executeQuery(query);
        // sudah pasti yang pertama
        String access_token = null;
        if (rs.next()) {
            access_token = rs.getString("access_token");
        }

        rs.close();
        stmt.close();
        return access_token;
    }
}
