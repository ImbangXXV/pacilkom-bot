package com.pacilkom.feats.siak.schedule.objects;

import org.json.JSONArray;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DayScheduleTest {
    String resp = "[{\"url\":\"https://api.cs.ui.ac.id/siakngcs/jadwal/6771/?format=json\",\"kd_kl" +
            "s_sc\":{\"url\":\"https://api.cs.ui.ac.id/siakngcs/kelas/565717/?format=json\",\"kd_kl" +
            "s\":\"565717\",\"nm_kls\":\"Advanced Program - B\",\"nm_mk_cl\":{\"url\":\"https://api" +
            ".cs.ui.ac.id/siakngcs/matakuliah/313/?format=json\",\"kd_mk\":\"CSCM602023\",\"nm_mk\"" +
            ":\"Pemrograman Lanjut\",\"kd_org\":\"01.00.12.01\",\"kd_kur\":\"01.00.12.01-2016\",\"j" +
            "ml_sks\":4},\"kd_kur_cl\":\"01.00.12.01-2016\",\"kd_mk_cl\":\"CSCM602023\",\"periode\"" +
            ":{\"url\":\"https://api.cs.ui.ac.id/siakngcs/periode/35/?format=json\",\"term\":2,\"ta" +
            "hun\":2017},\"pengajar\":[{\"nama\":\"Hadaiq Rolis Sanabila S.Kom., M.Kom.\",\"id_skem" +
            "a\":0,\"nm_skema\":\"Tidak Tetap\",\"maks_sks\":6}]},\"id_ruang\":{\"url\":\"https://a" +
            "pi.cs.ui.ac.id/siakngcs/ruangan/296/?format=json\",\"id_ruang\":\"296\",\"nm_ruang\":\"" +
            "2.2402\"},\"hari\":\"Senin\",\"jam_mulai\":\"13:00:00\",\"jam_selesai\":\"14:40:00\"}," +
            "{\"url\":\"https://api.cs.ui.ac.id/siakngcs/jadwal/6811/?format=json\",\"kd_kls_sc\":{" +
            "\"url\":\"https://api.cs.ui.ac.id/siakngcs/kelas/565725/?format=json\",\"kd_kls\":\"56" +
            "5725\",\"nm_kls\":\"RPL\",\"nm_mk_cl\":{\"url\":\"https://api.cs.ui.ac.id/siakngcs/mat" +
            "akuliah/316/?format=json\",\"kd_mk\":\"CSCM603125\",\"nm_mk\":\"Rekayasa Perangkat Lun" +
            "ak\",\"kd_org\":\"01.00.12.01\",\"kd_kur\":\"01.00.12.01-2016\",\"jml_sks\":3},\"kd_ku" +
            "r_cl\":\"01.00.12.01-2016\",\"kd_mk_cl\":\"CSCM603125\",\"periode\":{\"url\":\"https:/" +
            "/api.cs.ui.ac.id/siakngcs/periode/35/?format=json\",\"term\":2,\"tahun\":2017},\"penga" +
            "jar\":[{\"nama\":\"Maya Retno Ayu Setyautami S.Kom., M.Kom.\",\"id_skema\":0,\"nm_skem" +
            "a\":\"Tidak Tetap\",\"maks_sks\":6}]},\"id_ruang\":{\"url\":\"https://api.cs.ui.ac.id/" +
            "siakngcs/ruangan/247/?format=json\",\"id_ruang\":\"247\",\"nm_ruang\":\"2.2406\"},\"ha" +
            "ri\":\"Senin\",\"jam_mulai\":\"15:00:00\",\"jam_selesai\":\"15:50:00\"},{\"url\":\"htt" +
            "ps://api.cs.ui.ac.id/siakngcs/jadwal/6872/?format=json\",\"kd_kls_sc\":{\"url\":\"http" +
            "s://api.cs.ui.ac.id/siakngcs/kelas/565939/?format=json\",\"kd_kls\":\"565939\",\"nm_kl" +
            "s\":\"TBA - A\",\"nm_mk_cl\":{\"url\":\"https://api.cs.ui.ac.id/siakngcs/matakuliah/31" +
            "5/?format=json\",\"kd_mk\":\"CSCM602241\",\"nm_mk\":\"Teori Bahasa & Automata\",\"kd_o" +
            "rg\":\"01.00.12.01\",\"kd_kur\":\"01.00.12.01-2016\",\"jml_sks\":4},\"kd_kur_cl\":\"01" +
            ".00.12.01-2016\",\"kd_mk_cl\":\"CSCM602241\",\"periode\":{\"url\":\"https://api.cs.ui." +
            "ac.id/siakngcs/periode/35/?format=json\",\"term\":2,\"tahun\":2017},\"pengajar\":[{\"n" +
            "ama\":\"Ir. Suryana Setiawan M.Sc., Ph.D.\",\"id_skema\":1,\"nm_skema\":\"Skema Inti\"" +
            ",\"maks_sks\":12}]},\"id_ruang\":{\"url\":\"https://api.cs.ui.ac.id/siakngcs/ruangan/2" +
            "95/?format=json\",\"id_ruang\":\"295\",\"nm_ruang\":\"2.2304\"},\"hari\":\"Senin\",\"j" +
            "am_mulai\":\"10:00:00\",\"jam_selesai\":\"11:40:00\"}]";

    @Test
    public void parseJsonTest() {
        JSONArray json = new JSONArray(resp);
        List<ScheduleItem> items = DaySchedule.parseJson(json);
        assertEquals(3, items.size());
    }

    @Test
    public void hasGetter() throws NoSuchMethodException {
        DaySchedule obj = new DaySchedule(null, null, null, null, null);
        assertNull(obj.getDayName());
        assertNull(obj.getItems());
        assertNull(obj.getNpm());
        assertNull(obj.getTerm());
        assertNull(obj.getYear());
    }

    @Test
    public void toStringTest() {
        DaySchedule obj = new DaySchedule("Monday", "2", "2016", "1699999999", new ArrayList<>());
        assertEquals(obj.toString(), "\nHmm... there are no class that you must attend on " +
                "that day...");

        List<ScheduleItem> items = new ArrayList<>();
        List<String> lecturers = new ArrayList<>();
        lecturers.add("y");
        lecturers.add("z");

        items.add(new ScheduleItem(new CourseClass("a","b","c", lecturers,1),
                LocalTime.parse("12:00"), LocalTime.parse("14:00"), "e"));
        obj = new DaySchedule("Monday", "2", "2016", "1699999999", items);
        String result = obj.toString();

        assertTrue(result.contains("12:00" + " - " + "14:00" + "\n"));
        assertTrue(result.contains("- Course: a (c)\n"));
        assertTrue(result.contains("- Class: b\n"));
        assertTrue(result.contains("- Room: e\n"));
        assertTrue(result.contains("> y"));
        assertTrue(result.contains("> z"));
    }
}
