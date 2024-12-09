import java.time.DayOfWeek;
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
    abstract void checkPermission() throws NotPermitted;

    void usePermission() throws NotPermitted {
        checkPermission();
    }
}

abstract class TimePermission extends Permissions {
    //todo rework with StartTime
    final LocalDateTime start_time;
    boolean is_weekend;
    Period period;

    TimePermission(String string_time) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        this.start_time = LocalDateTime.parse(string_time, dateTimeFormatter);
    }

    TimePermission(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    abstract LocalDateTime getEndTime();

    boolean is_allowed_time() {
        return LocalDateTime.now().isAfter(start_time)
                && LocalDateTime.now().isBefore(getEndTime());
    }
}

class ShortPeriodPermission extends TimePermission implements CheckWeekday {

    ShortPeriodPermission(LocalDateTime start_time, Period period, boolean is_weekend) throws IllegalArgumentException {
        super(start_time);
        setVariables(period, is_weekend);
        checkPeriod();
    }

    ShortPeriodPermission(String string_time, Period period, boolean is_weekend) throws IllegalArgumentException {
        super(string_time);
        setVariables(period, is_weekend);
        checkPeriod();
    }

    private void setVariables(Period period, boolean is_weekend) {
        this.period = period;
        this.is_weekend = is_weekend;
    }

    private void checkPeriod() throws IllegalArgumentException {
        if (((DayOfWeek.of(start_time.get(ChronoField.DAY_OF_WEEK)) != DayOfWeek.MONDAY)
                || (period != Period.FIVE_DAYS))
                && ((period == Period.FIVE_DAYS)
                || (is_weekend(start_time) != is_weekend(getEndTime())))) {
            throw new IllegalArgumentException("The period includes weekends and weekdays at the same time.");
        }
    }

    @Override
    void checkPermission() throws NotPermitted {
        if (is_today_weekend() != is_weekend || !is_allowed_time()) {
            throw new NotPermitted("not allowed time permission");
        }
    }

    LocalDateTime getEndTime() {
        return (LocalDateTime) period.getDuration().addTo(this.start_time);
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
    void checkPermission() throws NotPermitted {
        if (is_today_weekend() != is_weekend) {
            throw new NotPermitted("not allowed time permission");
        }
        if (rest_of_trips < 1) {
            throw new NotPermitted("all trips are sold out");
        }
    }

    @Override
    void usePermission() throws NotPermitted {
        checkPermission();
        rest_of_trips--;
    }
}

class SeasonPermission extends TimePermission {
    LocalDateTime end_of_season_time;

    SeasonPermission(String string_time, LocalDateTime end_of_season_time) {
        super(string_time);
        this.end_of_season_time = end_of_season_time;
    }

    LocalDateTime getEndTime() {
        return end_of_season_time;
    }

    @Override
    void checkPermission() throws NotPermitted {
        if (!is_allowed_time()) {
            throw new NotPermitted("not allowed time permission");
        }
    }
}
