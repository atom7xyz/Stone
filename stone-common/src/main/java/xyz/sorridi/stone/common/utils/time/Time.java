package xyz.sorridi.stone.common.utils.time;

import java.sql.Timestamp;

/**
 * Time utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Time
{
    /**
     * Gets the current timestamp.
     *
     * @return Current timestamp.
     */
    public static Timestamp getStamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Gets the timestamp of the given time.
     *
     * @param time Time in millis.
     * @return Timestamp of the given time.
     */
    public static Timestamp getStamp(long time)
    {
        return new Timestamp(time);
    }
}
