package com.pacilkom.feats.siak.schedule.objects;

import org.json.JSONObject;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleItemTest {
    String res = "{\"url\":\"https://api.cs.ui.ac.id/siakngcs/jadwal/6771/?format=json\",\"kd_kls_" +
            "sc\":{\"url\":\"https://api.cs.ui.ac.id/siakngcs/kelas/565717/?format=json\",\"kd_kls" +
            "\":\"565717\",\"nm_kls\":\"Advanced Program - B\",\"nm_mk_cl\":{\"url\":\"https://api" +
            ".cs.ui.ac.id/siakngcs/matakuliah/313/?format=json\",\"kd_mk\":\"CSCM602023\",\"nm_mk\"" +
            ":\"Pemrograman Lanjut\",\"kd_org\":\"01.00.12.01\",\"kd_kur\":\"01.00.12.01-2016\",\"j" +
            "ml_sks\":4},\"kd_kur_cl\":\"01.00.12.01-2016\",\"kd_mk_cl\":\"CSCM602023\",\"periode\"" +
            ":{\"url\":\"https://api.cs.ui.ac.id/siakngcs/periode/35/?format=json\",\"term\":2,\"ta" +
            "hun\":2017},\"pengajar\":[{\"nama\":\"Hadaiq Rolis Sanabila S.Kom., M.Kom.\",\"id_skem" +
            "a\":0,\"nm_skema\":\"Tidak Tetap\",\"maks_sks\":6}]},\"id_ruang\":{\"url\":\"https://a" +
            "pi.cs.ui.ac.id/siakngcs/ruangan/296/?format=json\",\"id_ruang\":\"296\",\"nm_ruang\":\"" +
            "2.2402\"},\"hari\":\"Senin\",\"jam_mulai\":\"13:00:00\",\"jam_selesai\":\"14:40:00\"}";

    @Test
    public void parseJsonTest() {
        ScheduleItem obj = ScheduleItem.parseJson(new JSONObject(res));
        assertEquals("Pemrograman Lanjut", obj.getCourse().getSubject());
        assertEquals("13:00", obj.getStartTime().toString());
        assertEquals("14:40", obj.getEndTime().toString());
        assertEquals("2.2402", obj.getRoomName());
    }

    @Test
    public void hasGetter() {
        ScheduleItem obj = new ScheduleItem(null, null, null, null);
        assertNull(obj.getCourse());
        assertNull(obj.getStartTime());
        assertNull(obj.getEndTime());
        assertNull(obj.getRoomName());
    }

    @Test
    public void toStringTest() {
        List<String> lecturers = new ArrayList<>();
        lecturers.add("y");
        lecturers.add("z");

        ScheduleItem obj = new ScheduleItem(new CourseClass("a","b","c",lecturers,1),
                LocalTime.parse("12:00"), LocalTime.parse("14:00"), "e");
        String result = obj.toString();

        assertTrue(result.contains("12:00" + " - " + "14:00" + "\n"));
        assertTrue(result.contains("- Course: a (c)\n"));
        assertTrue(result.contains("- Class: b\n"));
        assertTrue(result.contains("- Room: e\n"));
        assertTrue(result.contains("> y"));
        assertTrue(result.contains("> z"));
    }
}
