import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;


interface CheckWeekday {

    static boolean check_weekend(LocalDateTime time) {
        DayOfWeek current_day = DayOfWeek.of(time.get(ChronoField.DAY_OF_WEEK));
        return current_day == DayOfWeek.SUNDAY
                || current_day == DayOfWeek.SATURDAY;
    }

    default boolean is_weekend(LocalDateTime time) {
        return check_weekend(time);
    }

    default boolean is_today_weekend() {
        return is_weekend(LocalDateTime.now());
    }
}

abstract class Permissions {
    abstract boolean checkPermission();

    public void usePermission() {
    }

    abstract String get_additional_info();

}

abstract class TimePermission extends Permissions {
    final LocalDateTime start_date_time;


    TimePermission(LocalDate date, Enums.StartTimes start_time) {
        this.start_date_time = start_time.getStartTime().atDate(date);
    }

    abstract LocalDateTime getEndTime();

    boolean is_allowed_time() {
        return LocalDateTime.now().isAfter(start_date_time)
                && LocalDateTime.now().isBefore(getEndTime());
    }

    public String get_additional_info() {
        return "Start time: " + start_date_time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}

class ShortPeriodPermission extends TimePermission implements CheckWeekday {
    boolean is_weekend;
    Enums.ShortPeriod period;

    ShortPeriodPermission(LocalDate date, Enums.StartTimes start_time, Enums.ShortPeriod duration, boolean is_weekend) throws IllegalArgumentException {
        super(date, start_time);
        setVariables(duration, is_weekend);
        checkShortPeriod();
    }


    private void setVariables(Enums.ShortPeriod duration, boolean is_weekend) {
        this.period = duration;
        this.is_weekend = is_weekend;
    }

    private void checkShortPeriod() throws IllegalArgumentException {
        if (((DayOfWeek.of(start_date_time.get(ChronoField.DAY_OF_WEEK)) != DayOfWeek.MONDAY)
                || (period != Enums.ShortPeriod.FIVE_DAYS))
                && ((period == Enums.ShortPeriod.FIVE_DAYS)
                || (is_weekend(start_date_time) != is_weekend(getEndTime())))) {
            throw new IllegalArgumentException("The Enums.ShortPeriod includes weekends and weekdays at the same time.");
        }
    }

    LocalDateTime getEndTime() {
        return (LocalDateTime) period.getDuration().addTo(this.start_date_time);
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
    public void usePermission() {
        if (checkPermission()) {
            rest_of_trips--;
        }
    }

    public String get_additional_info() {
        return "Rest of trips: " + rest_of_trips;
    }
}

class SeasonPermission extends TimePermission {
    LocalDateTime end_of_season_time = LocalDateTime.parse("2025-03-30T20:00:00.00");

    SeasonPermission(LocalDate date,
                     Enums.StartTimes start_time) {
        super(date, start_time);
    }

    LocalDateTime getEndTime() {
        return end_of_season_time;
    }

    @Override
    boolean checkPermission() {
        return is_allowed_time();
    }
}