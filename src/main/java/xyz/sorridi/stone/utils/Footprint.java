package xyz.sorridi.stone.utils;

import java.time.Duration;

/**
 * Memory footprint utils.
 *
 * @author Sorridi
 * @since 1.0
 */
public interface Footprint
{
    int ONE_KB = 1_024;
    int ONE_MB = ONE_KB;
    int TEN_MB = ONE_MB * 10;
    int HUNDRED_MB = ONE_MB * 100;

    Duration EXPIRE_1_MIN = Duration.ofMinutes(1);
    Duration EXPIRE_5_MINS = Duration.ofMinutes(5);
    Duration EXPIRE_10_MINS = Duration.ofMinutes(10);
    Duration EXPIRE_30_MINS = Duration.ofMinutes(30);
    Duration EXPIRE_1_HOUR = Duration.ofHours(1);
    Duration EXPIRE_6_HOURS = Duration.ofHours(6);
    Duration EXPIRE_12_HOURS = Duration.ofHours(12);
    Duration EXPIRE_1_DAY = Duration.ofDays(1);
    Duration EXPIRE_1_WEEK = Duration.ofDays(7);

    int SIZE_SMALL = 100;
    int SIZE_NORMAL = 1_000;
    int SIZE_MEDIUM = 10_000;
    int SIZE_LARGE = 100_000;
    int SIZE_HUGE = 1_000_000;

    /**
     * Returns the memory usage of the given string.
     *
     * @param string The string.
     * @return The memory usage of the given string.
     */
    default int usageOf(String string)
    {
        return 8 * ((string.length() * 2 + 45) / 8);
    }
}
