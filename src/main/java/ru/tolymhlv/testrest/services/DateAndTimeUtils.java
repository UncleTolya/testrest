package ru.tolymhlv.testrest.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class DateAndTimeUtils {

    @NotBlank
    @Value("#{T(java.time.ZoneId).of('${ru.tolymhlv.testrest.zoneId}')}")
    private ZoneId zoneId;

    public LocalDateTime now() {
        final Instant instant = Instant.now();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    public LocalDateTime startOfDay(@NonNull final LocalDateTime now) {
        return now.toLocalDate().atStartOfDay(zoneId).toLocalDateTime();
    }

    public LocalDateTime stringToTime(@NonNull final String stringWithDate) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return LocalDateTime.parse(stringWithDate, formatter);
    }
}
