package he4rt.open.tibia.base.utils;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

public class ScheduleUtils {

    public static boolean cancel(ScheduledFuture<?> schedule, boolean type) {
        if (Objects.nonNull(schedule) && !schedule.isCancelled() && !schedule.isDone()) {
            return schedule.cancel(type);
        }
        return false;
    }

}