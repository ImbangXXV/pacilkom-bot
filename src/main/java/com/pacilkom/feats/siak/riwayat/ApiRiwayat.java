package com.pacilkom.feats.siak.riwayat;

import com.pacilkom.csuilogin.SessionDatabase;
import com.pacilkom.feats.login.LoginVerifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ApiRiwayat {

    private static final String CLIENT_ID = "X3zNkFmepkdA47ASNMDZRX3Z9gqSU1Lwywu5WepG";
    private static final String API_LINK = "https://api.cs.ui.ac.id/siakngcs/mahasiswa/";

    public static JSONArray getJson(Integer userId) throws IOException, SQLException {
        String result = "";
        URL url = new URL(API_LINK + LoginVerifier.verify(userId)
        + "/riwayat/?access_token=" + SessionDatabase.getInstance().getAccessToken(userId)
        + "&client_id=" + CLIENT_ID);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        while ((line = rd.readLine()) != null) {
            result += line;
        }

        rd.close();
        return new JSONArray(result);
    }

    public static String getRiwayat(Integer userId) throws IOException, SQLException {
        String output = "";
        JSONArray json = getJson(userId);
        for (int i = 0;i < json.length();i++) {
            JSONObject skrip = (JSONObject) json.get(i);
            JSONObject kelas = (JSONObject) skrip.get("kelas");
            JSONObject matkul = (JSONObject) kelas.get("nm_mk_cl");
            output += "Nama Matkul : " + matkul.get("nm_mk") + "\n";
            output += "Kelas : " + kelas.get("nm_kls") + "\n";
            output += "SKS : " + matkul.get("jml_sks") + "\n";
            output += "Periode : " + skrip.get("tahun") + " / "
                    + skrip.get("term") + "\n";
            output += "Nilai : " + skrip.get("nilai") + "\n";
        }
        return output;
    }

    public static List<String> getKuliah(Integer userId) throws IOException, SQLException {
        List<String> output = new LinkedList<>();
        JSONArray json = getJson(userId);
        for (int i = 0;i < json.length();i++) {
            JSONObject skrip = (JSONObject) json.get(i);
            JSONObject kelas = (JSONObject) skrip.get("kelas");
            JSONObject matkul = (JSONObject) kelas.get("nm_mk_cl");
            output.add(matkul.getString("nm_mk"));
        }
        return output;
    }

    public static void main (String[] args) {

    }
}
