import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.temporal.ChronoField;

public abstract class Permissions {
    final boolean is_weekend;
    Permissions(boolean is_weekend) {
        this.is_weekend = is_weekend;
    }
    final boolean is_today_weekend() {
        LocalDate today = LocalDate.now();
        return DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SUNDAY ||
                DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SATURDAY;
    }
    abstract boolean checkPermission();
    abstract void usePermission();
}

class TimePermission extends Permissions {
    final LocalDateTime start_time;
    final Duration duration;
    TimePermission(boolean is_weekend, LocalDateTime start_time,Duration duration) {
        super(is_weekend);
        this.start_time = start_time;
        this.duration = duration;
    }

    @Override
    boolean checkPermission() {
        return is_today_weekend() == this.is_weekend
                && LocalDateTime.now().isAfter(this.start_time)
                && LocalDateTime.now().isBefore((LocalDateTime) this.duration.addTo(this.start_time));
    }

    @Override
    void usePermission() {
    }

}

class CountPermission extends Permissions {
    private int rest_of_trips;
    CountPermission(boolean is_weekend, int trips) {
        super(is_weekend);
        this.rest_of_trips = trips;
    }

    @Override
    boolean checkPermission() {
        return is_today_weekend() == this.is_weekend
                && this.rest_of_trips > 0;
    }

    @Override
    void usePermission() {
        if (checkPermission()) {
            this.rest_of_trips--;
        }
    }

}
