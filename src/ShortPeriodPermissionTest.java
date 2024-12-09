import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ShortPeriodPermissionTest {
    LocalDateTime start_time = LocalDateTime.now().minusHours(3);
    boolean is_weekend = CheckWeekday.check_weekend(LocalDateTime.now());
    final ShortPeriodPermission sh_p_p = new ShortPeriodPermission(start_time, Period.HALF_DAY, is_weekend);

    //    private final LocalDateTime test_time = LocalDateTime.parse("11:00 22.11.2024");
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

    @org.junit.jupiter.api.Test
    void getEndTime() {
        LocalDateTime correct_end_time = LocalDateTime.now().plusHours(1);
        assertEquals(correct_end_time.getSecond(), sh_p_p.getEndTime().getSecond());
    }

//    @org.junit.jupiter.api.Test
//    void checkPermission() {
//        assertTrue(sh_p_p.checkPermission());
//    }
}