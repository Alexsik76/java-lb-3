import java.time.Duration;
import java.time.LocalTime;

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
