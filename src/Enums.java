import java.time.Duration;
import java.time.LocalTime;

public class Enums {
    public enum ShortPeriod {
        HALF_DAY(Duration.ofHours(4)),
        DAY(Duration.ofDays(1)),
        TWO_DAYS(Duration.ofDays(2)),
        FIVE_DAYS(Duration.ofDays(5));

        private final Duration duration;

        ShortPeriod(Duration duration) {
            this.duration = duration;
        }

        public Duration getDuration() {
            return duration;
        }
    }

    public enum StartTimes {
        AM(LocalTime.parse("09:00")),
        PM(LocalTime.parse("13:00"));

        private final LocalTime startTime;

        StartTimes(LocalTime st) {
            startTime = st;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

    }
}


