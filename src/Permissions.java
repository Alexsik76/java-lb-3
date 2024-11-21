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

    public default boolean is_weekend(LocalDateTime time) {
        LocalDateTime today = LocalDateTime.now();
        return DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SUNDAY ||
                DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SATURDAY;
    }
    default boolean is_today_weekend(){
        return is_weekend(LocalDateTime.now());
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
    ShortPeriodPermission(LocalDateTime start_time, Period duration, boolean is_weekend) throws NotCorrectPeriod {
        super(start_time);
        this.period = duration;
        this.is_weekend = is_weekend;
        if (!is_correct_period()){

            throw new NotCorrectPeriod("The period includes weekends and weekdays at the same time");
        }
    }

    private boolean is_correct_period() {
        System.out.println((DayOfWeek.of(start_time.get(ChronoField.DAY_OF_WEEK)) != DayOfWeek.MONDAY)
                && (is_weekend(start_time) == is_weekend(getEndTime())));
        return ((DayOfWeek.of(start_time.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.MONDAY)
                && (period == Period.FIVE_DAYS))
                || ((DayOfWeek.of(start_time.get(ChronoField.DAY_OF_WEEK)) != DayOfWeek.MONDAY)
                && (is_weekend(start_time) == is_weekend(getEndTime())));
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
