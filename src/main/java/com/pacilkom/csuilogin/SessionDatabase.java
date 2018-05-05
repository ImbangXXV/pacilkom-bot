package com.pacilkom.csuilogin;

import com.pacilkom.bot.PacilkomBot;

import java.sql.*;

public class SessionDatabase {
    private static SessionDatabase instance = new SessionDatabase();
    private Connection c;

    private SessionDatabase() {
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println(System.getenv("DATABASE_URL"));

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

//    public void createSession(int user_id, String access_token) throws SQLException {
//        Statement stmt = c.createStatement();
//        String query;
//        if (getAccessToken(user_id) == null) {
//            // use INSERT
//            query = "INSERT INTO BOT_USER (user_id, access_token)\n"
//                    + "VALUES (" + user_id + ", '" + access_token + "');";
//        } else {
//            // use UPDATE
//            query = "UPDATE BOT_USER SET access_token = '" + access_token + "'\n"
//                    + "WHERE user_id = " + user_id + ";";
//        }
//
//        stmt.executeUpdate(query);
//        stmt.close();
//    }
//
//    public void deleteSession(int user_id) throws SQLException {
//        Statement stmt = c.createStatement();
//        String query = "DELETE FROM BOT_USER WHERE user_id = " + user_id + ";";
//        stmt.executeUpdate(query);
//        stmt.close();
//    }
//
//    public String getAccessToken(int user_id) throws SQLException {
//        Statement stmt = c.createStatement();
//        String query = "SELECT access_token\n"
//                + "FROM BOT_USER\n"
//                + "WHERE user_id = " + user_id + ";";
//
//        ResultSet rs = stmt.executeQuery(query);
//        // sudah pasti yang pertama
//        String access_token = null;
//        if (rs.next()) {
//            access_token = rs.getString("access_token");
//        }
//
//        rs.close();
//        stmt.close();
//        return access_token;
//    }
}