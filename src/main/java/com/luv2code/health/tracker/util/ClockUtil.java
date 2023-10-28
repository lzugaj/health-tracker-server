package com.luv2code.health.tracker.util;

import java.time.Clock;
import java.time.Instant;

import static java.time.Clock.fixed;
import static java.time.Clock.systemDefaultZone;
import static java.time.ZoneOffset.UTC;

public class ClockUtil {

    private static Clock systemClock = systemDefaultZone();

    public static void useFixedClockAt(Instant instant) {
        systemClock = fixed(instant, UTC);
    }

    public static void resetClock() {
        systemClock = systemDefaultZone();
    }

    public static Clock getClock() {
        return systemClock;
    }
}
