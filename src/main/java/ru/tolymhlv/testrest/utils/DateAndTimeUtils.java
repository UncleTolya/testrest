package ru.tolymhlv.testrest.utils;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class DateAndTimeUtils {
    private final ZoneId zoneId;

    @Autowired
    public DateAndTimeUtils(
            @NotBlank
            @Value("#{T(java.time.ZoneId).of('${ru.tolymhlv.testrest.zoneId}')}")
                                    final ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public LocalDateTime now() {
        return LocalDateTime.now(zoneId);
    }

    public LocalDateTime startOfDay(@NonNull final LocalDateTime now) {
        return now.toLocalDate().atStartOfDay(zoneId).toLocalDateTime();
    }

    public LocalDateTime stringToTime(@NonNull final String stringWithDate) throws IllegalArgumentException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        try {
            return LocalDateTime.parse(stringWithDate, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException();
        }
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
