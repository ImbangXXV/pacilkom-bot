package com.pacilkom.feats.siak.schedule.api;

import com.pacilkom.feats.siak.schedule.objects.DaySchedule;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ScheduleAPITest {
    @Test
    public void getApiResponseTest() {
        DaySchedule result = ScheduleAPI.getApiResponse("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz",
                "1699999999", "2016", "1", "Monday");
        // the access token is dummy and invalid in CSUI server
        assertNull(result);
    }

    @Test
    public void translateDayTest() {
        assertEquals(ScheduleAPI.translateDay("Monday"), "Senin");
        assertEquals(ScheduleAPI.translateDay("Tuesday"), "Selasa");
        assertEquals(ScheduleAPI.translateDay("Wednesday"), "Rabu");
        assertEquals(ScheduleAPI.translateDay("Thursday"), "Kamis");
        assertEquals(ScheduleAPI.translateDay("Friday"), "Jumat");
        assertEquals(ScheduleAPI.translateDay("Saturday"), "Sabtu");
        assertEquals(ScheduleAPI.translateDay("hehehe"), null);
    }
}
