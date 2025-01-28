import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ShortPeriodPermissionTest {
    boolean is_weekend = CheckWeekday.check_weekend(LocalDateTime.now());
    final ShortPeriodPermission sh_p_p = new ShortPeriodPermission(LocalDate.now(), Enums.StartTimes.AM, Enums.ShortPeriod.DAY, is_weekend);

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
    void checkPermission() {
        assertTrue(sh_p_p.checkPermission());
    }
}