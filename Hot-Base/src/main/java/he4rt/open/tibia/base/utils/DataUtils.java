package he4rt.open.tibia.base.utils;

import lombok.experimental.UtilityClass;

import java.time.*;

@UtilityClass
public class DataUtils {

    public static Long localDateTimeToLong(LocalDateTime localDateTime) {

        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();

    }

    public static DayOfWeek getDayOfWeek(LocalDate ld) {
        return ld.getDayOfWeek();
    }

    public static DayOfWeek getDayOfWeek() {
        return getDayOfWeek(LocalDate.now());
    }

    public static int getDayOMfonth(LocalDate ld) {
        return ld.getDayOfMonth();
    }

    public static int getDayOMfonth() {
        return getDayOMfonth(LocalDate.now());
    }

    public static long getDateToLong(LocalDateTime date) {
        return date.atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli();
    }

    public long getNow() {
        return System.currentTimeMillis();
    }

}
