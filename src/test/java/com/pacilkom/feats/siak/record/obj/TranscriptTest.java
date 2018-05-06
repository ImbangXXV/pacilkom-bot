package com.pacilkom.feats.siak.record.obj;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TranscriptTest {

    private int id = 11;
    private int id2 = 111;
    private String courseName = "Dasar-Dasar Laravel Coding";
    private String courseName2 = "Dasar-Dasar Laravel Coding";
    private String lecturer = "Pak Thanos M. Kom";
    private String lecturer2 = "Pak Thanos M. Kom";
    private int credit = 3;
    private int credit2 = 2;
    private int year = 2017;
    private int year2 = 2016;
    private int term = 1;
    private int term2 = 2;
    private String grade = "A";
    private String grade2 = "C";
    private String json = "{\n" +
            "    \"url\": \"https://api.cs.ui.ac.id/siakngcs/record-mahasiswa/132827/\",\n" +
            "    \"npm\": \"1606878713\",\n" +
            "    \"kelas\": {\n" +
            "      \"url\": \"https://api.cs.ui.ac.id/siakngcs/kelas/524714/\",\n" +
            "      \"kd_kls\": \"524714\",\n" +
            "      \"nm_kls\": \"PSD - C\",\n" +
            "      \"nm_mk_cl\": {\n" +
            "        \"url\": \"https://api.cs.ui.ac.id/siakngcs/matakuliah/311/\",\n" +
            "        \"kd_mk\": \"CSCM601150\",\n" +
            "        \"nm_mk\": \"Pengantar Sistem Dijital\",\n" +
            "        \"kd_org\": \"01.00.12.01\",\n" +
            "        \"kd_kur\": \"01.00.12.01-2016\",\n" +
            "        \"jml_sks\": 4\n" +
            "      },\n" +
            "      \"kd_kur_cl\": \"01.00.12.01-2016\",\n" +
            "      \"kd_mk_cl\": \"CSCM601150\",\n" +
            "      \"periode\": {\n" +
            "        \"url\": \"https://api.cs.ui.ac.id/siakngcs/periode/31/\",\n" +
            "        \"term\": 1,\n" +
            "        \"tahun\": 2016\n" +
            "      },\n" +
            "      \"pengajar\": [\n" +
            "        {\n" +
            "          \"nama\": \"Ir. Adhi Yuniarto Laurentius Yohannes M.Kom.\",\n" +
            "          \"id_skema\": 3,\n" +
            "          \"nm_skema\": \"Struktural Univ\",\n" +
            "          \"maks_sks\": 11\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"kd_kls\": \"524714\",\n" +
            "    \"kd_kur\": \"01.00.12.01-2016\",\n" +
            "    \"kd_mk\": \"CSCM601150\",\n" +
            "    \"kd_org\": \"01.00.12.01\",\n" +
            "    \"term\": 1,\n" +
            "    \"tahun\": 2016,\n" +
            "    \"nilai\": \"A\"\n" +
            "  }";
    private String json2 = " {\n" +
            "    \"url\": \"https://api.cs.ui.ac.id/siakngcs/record-mahasiswa/1789180/\",\n" +
            "    \"npm\": \"1606878713\",\n" +
            "    \"kelas\": {\n" +
            "      \"url\": \"https://api.cs.ui.ac.id/siakngcs/kelas/553777/\",\n" +
            "      \"kd_kls\": \"553777\",\n" +
            "      \"nm_kls\": \"Agama Kristen A\",\n" +
            "      \"nm_mk_cl\": {\n" +
            "        \"url\": \"https://api.cs.ui.ac.id/siakngcs/matakuliah/950/\",\n" +
            "        \"kd_mk\": \"UIGE600012\",\n" +
            "        \"nm_mk\": \"MPK Agama Kristen Protestan\",\n" +
            "        \"kd_org\": \"06.00.12.01\",\n" +
            "        \"kd_kur\": \"06.00.12.01-2016\",\n" +
            "        \"jml_sks\": 2\n" +
            "      },\n" +
            "      \"kd_kur_cl\": \"06.00.12.01-2016\",\n" +
            "      \"kd_mk_cl\": \"UIGE600012\",\n" +
            "      \"periode\": {\n" +
            "        \"url\": \"https://api.cs.ui.ac.id/siakngcs/periode/34/\",\n" +
            "        \"term\": 1,\n" +
            "        \"tahun\": 2017\n" +
            "      },\n" +
            "      \"pengajar\": [\n" +
            "        {\n" +
            "          \"nama\": null,\n" +
            "          \"id_skema\": 0,\n" +
            "          \"nm_skema\": null,\n" +
            "          \"maks_sks\": null\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"kd_kls\": \"553777\",\n" +
            "    \"kd_kur\": \"06.00.12.01-2016\",\n" +
            "    \"kd_mk\": \"UIGE600012\",\n" +
            "    \"kd_org\": \"01.00.12.01\",\n" +
            "    \"term\": 1,\n" +
            "    \"tahun\": 2017,\n" +
            "    \"nilai\": \"A\"\n" +
            "  }";

    private Transcript sample;

    @Before
    public void setUp() {
        sample = new Transcript(id, courseName, lecturer, credit,
                year, term, grade);
    }

    @Test
    public void testSetterGetterWorkedProperly() {
        sample.setId(id2);
        assertEquals(id2, sample.getId());
        sample.setSubject(courseName2);
        assertTrue(sample.getSubject().equals(courseName2));
        sample.setLecturer(lecturer2);
        assertTrue(sample.getLecturer().equals(lecturer2));
        sample.setCredit(credit2);
        assertEquals(credit2, sample.getCredit());
        sample.setYear(year2);
        assertEquals(year2, sample.getYear());
        sample.setTerm(term2);
        assertEquals(term2, sample.getTerm());
        sample.setGrade(grade2);
        assertTrue(sample.getGrade().equals(grade2));
    }

    @Test
    public void testReviewMethodWorkingProperly() {
        String review = sample.summarize();
        assertTrue(review.contains(courseName));
        assertTrue(review.contains(lecturer));
        assertTrue(review.contains(String.valueOf(credit)));
        assertTrue(review.contains(String.valueOf(year)));
        assertTrue(review.contains(String.valueOf(term)));
        assertTrue(review.contains(grade));
    }

    @Test
    public void testParserWorkProperly() {
        JSONObject jsonObj = new JSONObject(json);
        Transcript parsedObj = Transcript.convertJson(jsonObj);
        String review = parsedObj.summarize();

        assertEquals("Pengantar Sistem Dijital",
                parsedObj.getSubject());
        assertEquals("Ir. Adhi Yuniarto Laurentius Yohannes M.Kom."
                , parsedObj.getLecturer());
        assertEquals(524714, parsedObj.getId());
        assertEquals(2016, parsedObj.getYear());
        assertEquals(1, parsedObj.getTerm());
        assertEquals("A", parsedObj.getGrade());
        assertEquals(4, parsedObj.getCredit());
        assertTrue(review.contains("Alright, that should be all."));
    }

    @Test
    public void testParserWorkProperlyOnIncompleData() {
        JSONObject jsonObj = new JSONObject(json2);
        Transcript parsedObj = Transcript.convertJson(jsonObj);
        String review = parsedObj.summarize();

        assertEquals("MPK Agama Kristen Protestan",
                parsedObj.getSubject());
        assertEquals("Unidentified"
        , parsedObj.getLecturer());
        assertEquals(553777, parsedObj.getId());
        assertEquals(2017, parsedObj.getYear());
        assertEquals(1, parsedObj.getTerm());
        assertEquals("A", parsedObj.getGrade());
        assertEquals(2, parsedObj.getCredit());
        assertTrue(review.contains("We're sorry that some data are missing... "
                + "That's all we got from our source..."));
    }

}