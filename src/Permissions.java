import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

interface CheckWeekday {

    default boolean is_weekend(@org.jetbrains.annotations.NotNull LocalDateTime time) {
        return DayOfWeek.of(time.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SUNDAY ||
                DayOfWeek.of(time.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SATURDAY;
    }

    default boolean is_today_weekend() {
        return is_weekend(LocalDateTime.now());
    }
}

abstract class Permissions {
    abstract boolean checkPermission();

    void usePermission() {
    }
}

abstract class TimePermission extends Permissions {

    final LocalDateTime start_time;

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
    boolean is_weekend;
    Period period;

    ShortPeriodPermission(LocalDateTime start_time, Period duration, boolean is_weekend) throws NotCorrectPeriod {
        super(start_time);
        setVariables(duration, is_weekend);
        checkPeriod();
    }

    ShortPeriodPermission(String string_time, Period duration, boolean is_weekend) throws NotCorrectPeriod {
        super(string_time);
        setVariables(duration, is_weekend);
        checkPeriod();
    }

    private void setVariables(Period duration, boolean is_weekend) {
        this.period = duration;
        this.is_weekend = is_weekend;
    }

    private void checkPeriod() throws NotCorrectPeriod {
        if (((DayOfWeek.of(start_time.get(ChronoField.DAY_OF_WEEK)) != DayOfWeek.MONDAY)
                || (period != Period.FIVE_DAYS))
                && ((period == Period.FIVE_DAYS)
                || (is_weekend(start_time) != is_weekend(getEndTime())))) {
            throw new NotCorrectPeriod("The period includes weekends and weekdays at the same time.");
        }
    }

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
    boolean checkPermission() {
        return is_allowed_time();
    }
}


