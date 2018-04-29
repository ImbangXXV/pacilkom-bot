package com.pacilkom.csuilogin;

import java.sql.*;

public class SessionDatabase {
    private static SessionDatabase instance = new SessionDatabase();
    private Connection c;
    private static final String DATABASE_URI = "jdbc:postgresql://ec2-54-75-239-237"
            + ".eu-west-1.compute.amazonaws.com:5432/d78hijtf1urdja?sslmode=require&ssl=true&"
            + "sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String DATABASE_USER = "giibaqgklxangd";
    private static final String DATABASE_PASSWORD = "1019e764eb3232833c55a4acca089f9f"
            + "214af13bfba09b591573aec9c70eddb8";

    private SessionDatabase() {
        try {
            Class.forName("org.postgresql.Driver");

            c = DriverManager.getConnection(DATABASE_URI, DATABASE_USER, DATABASE_PASSWORD);
            try {
                createBotUserTable();
            } catch (SQLException e) {
                System.out.println("Skipping create table because it already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionDatabase getInstance() {
        return instance;
    }

    private void createBotUserTable() throws SQLException {
        Statement stmt = c.createStatement();
        String query = "CREATE TABLE BOT_USER(\n"
                + "user_id INT PRIMARY KEY NOT NULL,\n"
                + "access_token TEXT NOT NULL)";
        stmt.executeUpdate(query);
        stmt.close();
    }

    public void createSession(int user_id, String access_token) throws SQLException {
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

    public String getAccessToken(int user_id) throws SQLException {
        Statement stmt = c.createStatement();
        String query = "SELECT *\n"
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
