import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;


abstract class Permissions {
    abstract boolean checkPermission();
    void usePermission(){
    }
}

interface CheckWeekday {
    default boolean is_today_weekend() {
        LocalDate today = LocalDate.now();
        return DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SUNDAY ||
                DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SATURDAY;
    }
}

abstract class TimePermission extends Permissions {
    final LocalDateTime start_time;

    TimePermission(LocalDateTime start_time) {
        this.start_time = start_time;
    }
    abstract LocalDateTime getEndTime();

    boolean is_allowed_time() {
        return LocalDateTime.now().isAfter(start_time)
                && LocalDateTime.now().isBefore(getEndTime());
    }
}

class SeasonPermission extends TimePermission {
    LocalDateTime end_of_season_time;
    SeasonPermission(LocalDateTime start_time, LocalDateTime end_of_season_time) {
       super(start_time);
       this.end_of_season_time = end_of_season_time;
    }
    LocalDateTime getEndTime() {
        return end_of_season_time;
    };
    @Override
    boolean checkPermission() {
        return is_allowed_time();
    }
}

class ShortPeriodPermission extends TimePermission implements CheckWeekday {
    boolean is_weekend;
    Period period;
    ShortPeriodPermission( LocalDateTime start_time, Period duration, boolean is_weekend) {
        super(start_time);
        this.period = duration;
        this.is_weekend = is_weekend;
    }
    //TODO Add check period

    LocalDateTime getEndTime() {
        return (LocalDateTime) period.getDuration().addTo(this.start_time);
    }

    @Override
    boolean checkPermission() {
        return is_today_weekend() == is_weekend && is_allowed_time();
    }
}

class CountPermission extends Permissions implements CheckWeekday {
    boolean is_weekend;
    private int rest_of_trips;
    CountPermission(boolean is_weekend, int trips) {
        this.is_weekend = is_weekend;
        this.rest_of_trips = trips;
    }

    @Override
    boolean checkPermission() {
        return is_today_weekend() == is_weekend
                && rest_of_trips > 0;
    }

    @Override
    void usePermission() {
        if (checkPermission()) {
            rest_of_trips--;
        }
    }
}
