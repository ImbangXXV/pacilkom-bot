package com.pacilkom.csuilogin;

import com.pacilkom.bot.PacilkomBot;

import java.sql.*;

public class SessionDatabase {
    private static SessionDatabase instance = new SessionDatabase();
    private Connection c;

    private SessionDatabase() {
        try {
            Class.forName("org.postgresql.Driver");

            // Parsing database url
            String url = "jdbc:postgresql://" + PacilkomBot.DATABASE_URL.split("@")[1];
            String user = PacilkomBot.DATABASE_URL.split(":")[1].substring(2);
            String pass = PacilkomBot.DATABASE_URL.split(":")[2].split("@")[0];

            c = DriverManager.getConnection(url, user, pass);
            try {
                createBotUserTable();
            } catch (SQLException e) {
                System.out.println("Requested table is already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionDatabase getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return c;
    }

    private void createBotUserTable() throws SQLException {
        Statement stmt = c.createStatement();
        String query = "CREATE TABLE BOT_USER(\n"
                + "user_id INT PRIMARY KEY NOT NULL,\n"
                + "access_token TEXT NOT NULL)";
        stmt.executeUpdate(query);
        stmt.close();
    }
}