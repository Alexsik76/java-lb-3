import java.time.Duration;

public enum Period {
    HALF_DAY(Duration.ofHours(4)),
    DAY(Duration.ofDays(1)),
    TWO_DAYS(Duration.ofDays(2)),
    FIVE_DAYS(Duration.ofDays(5));

    private final Duration duration;
    Period (Duration duration){
        this.duration = duration;
    }
    public Duration getDuration() {
        return duration;
    }

}