import java.time.Duration;
import java.time.LocalTime;

public enum StartTimeSkyPass {
    AM(LocalTime.parse("09:00")),
    PM(LocalTime.parse("13:00"));

    private final LocalTime startTime;

    StartTimeSkyPass(LocalTime st) {
        startTime = st;
    }

    public LocalTime getTime() {
        return startTime;
    }

}