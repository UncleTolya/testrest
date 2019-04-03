package ru.tolymhlv.testrest.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class DateAndTimeUtils {

    @NotBlank
    @Value("${ru.tolymhlv.testrest.zoneId}")
    private String stringZoneId;

    public LocalDateTime now() {
        final Instant instant = Instant.now();
        return LocalDateTime.ofInstant(instant, getZoneId());
    }

    public LocalDateTime startOfDate(final LocalDateTime now) {
        return now.toLocalDate().atStartOfDay(getZoneId()).toLocalDateTime();
    }

    private ZoneId getZoneId() {
        return ZoneId.of(stringZoneId);
    }

    public LocalDateTime stringToTime(final String string) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return LocalDateTime.parse(string, formatter);
    }
}
