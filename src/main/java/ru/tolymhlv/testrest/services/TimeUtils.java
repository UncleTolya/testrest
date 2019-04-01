package ru.tolymhlv.testrest.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TimeUtils {

    @Value("${zoneId}")
    private String zoneId;

    public LocalDateTime now() {
        final Instant instant = Instant.now();
        return LocalDateTime.ofInstant(instant, getZoneId());
    }

    public LocalDateTime startOfDate(final LocalDateTime now) {
        return now.toLocalDate().atStartOfDay(getZoneId()).toLocalDateTime();
    }

    private ZoneId getZoneId() {
        return ZoneId.of(zoneId);
    }

    public LocalDateTime stringToTime(final String string) {
        return null;
    }
}
