import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class ShortPeriodPermissionTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String start_date = LocalDate.now().format(formatter);
    StartTimeSkyPass start_time = StartTimeSkyPass.AM;
    DurationSkyPass duration = DurationSkyPass.DAY;
    boolean is_weekend = CheckWeekday.check_weekend(LocalDateTime.now());
    final ShortPeriodPermission sh_p_p = new ShortPeriodPermission(start_date, start_time, duration, is_weekend);


    ShortPeriodPermissionTest() throws IllegalArgumentException {
    }

    @org.junit.jupiter.api.Test
    void is_allowed_time() {
        assertTrue(sh_p_p.is_allowed_time());
    }

    @org.junit.jupiter.api.Test
    void is_today_weekend() {
        assertFalse(sh_p_p.is_today_weekend());
    }

//    @org.junit.jupiter.api.Test
//    void getEndTime() {
//        LocalDateTime correct_end_time = LocalDateTime.now().plusHours(1);
//        assertEquals(correct_end_time.getSecond(), sh_p_p.getEndTime().getSecond());
//    }

    @org.junit.jupiter.api.Test
    void checkPermission() throws NotPermitted {
        assertDoesNotThrow(sh_p_p::checkPermission);
    }
}