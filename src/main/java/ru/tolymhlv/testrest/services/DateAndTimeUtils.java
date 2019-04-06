package ru.tolymhlv.testrest.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    public LocalDateTime stringToTime(@NonNull final String stringWithDate) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return LocalDateTime.parse(stringWithDate, formatter);
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
