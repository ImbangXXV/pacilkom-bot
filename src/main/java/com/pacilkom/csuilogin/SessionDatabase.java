package com.pacilkom.csuilogin;

import java.sql.*;

public class SessionDatabase {
    private static SessionDatabase instance = new SessionDatabase();
    private Connection c;
    private static final String DATABASE_URI = "postgres://giibaqgklxangd:1019e764e"
            + "b3232833c55a4acca089f9f214af13bfba09b591573aec9c70eddb8@ec2-54-"
            + "75-239-237.eu-west-1.compute.amazonaws.com:5432/d78hijtf1urdja";
    private static final String DATABASE_USER = "giibaqgklxangd";
    private static final String DATABASE_PASSWORD = "1019e764eb3232833c55a4acca089f9f"
            + "214af13bfba09b591573aec9c70eddb8";

    private SessionDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(DATABASE_URI, DATABASE_USER, DATABASE_PASSWORD);
            if (!isTableExists()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionDatabase getInstance() {
        return instance;
    }

    private boolean isTableExists() throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        ResultSet tables = meta.getTables(null, null, "BOT_USER", null);
        boolean result = tables.next();

        tables.close();
        return result;
    }

    private void createBotUserTable() throws SQLException {
        Statement stmt = c.createStatement();
        String query = "CREATE TABLE BOT_USER(\n"
                + "user_id INT PRIMARY KEY NOT NULL\n"
                + "access_token TEXT NOT NULL)";
        stmt.executeUpdate(query);
        stmt.close();
    }

    public void createSession(int user_id, String access_token) throws SQLException {
        Statement stmt = c.createStatement();
        String query;
        if (getAccessToken(user_id) == null) {
            // use INSERT
            query = "INSERT INTO BOT_USER (id_user, access_token)\n"
                    + "VALUES ('" + user_id + "', '" + access_token + "')";
        } else {
            // use UPDATE
            query = "UPDATE BOT_USER SET access_token = '" + access_token + "'\n"
                    + "WHERE user_id = '" + user_id + "'";
        }

        stmt.executeUpdate(query);
        stmt.close();
    }

    public String getAccessToken(int user_id) throws SQLException {
        Statement stmt = c.createStatement();
        String query = "SELECT access_token"
                + "FROM BOT_USER"
                + "WHERE user_id = '" + user_id + "'";

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
