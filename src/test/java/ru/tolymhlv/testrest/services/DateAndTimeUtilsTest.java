package ru.tolymhlv.testrest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DateAndTimeUtilsTest {

    @Autowired
    private DateAndTimeUtils dateAndTimeUtils;

    Clock fixed;

    @BeforeEach
    public void setUp() {
        fixed = Clock.fixed(Instant.now(), dateAndTimeUtils.getZoneId());
    }


    @Test
    public void nowReturnCorrect() {
        final LocalDateTime nowTest = LocalDateTime.now(fixed);
        final LocalDateTime now = dateAndTimeUtils.now();

        final String nowTestString = nowTest.toString().substring(0, 18);
        final String nowString = now.toString().substring(0, 18);

        assertEquals(nowString, nowTestString);
    }

    @Test
    public void startOfDayReturnCorrect() {
        final LocalDateTime now = LocalDateTime.now(fixed);
        final LocalDateTime startOfDayTest = dateAndTimeUtils.startOfDay(now);

        final String nowString = now.toString().substring(0, 11) + "00:00";
        final String startOfDayTestSring = startOfDayTest.toString();

        assertEquals(nowString, startOfDayTestSring);
    }

    @Test
    public void stringToTimeCorrect() {
        String input = "1992-04-15-03-15-15";
        LocalDateTime time = dateAndTimeUtils.stringToTime(input);
        assertEquals("1992-04-15T03:15:15", time.toString());
    }

    @Test
    public void stringToTimeIncorrect() {
        String input = "1992-04-15-03-16-15";
        LocalDateTime time = dateAndTimeUtils.stringToTime(input);
        assertNotEquals("1992-04-15T03:15:16", time.toString());
    }

    @Test
    public void stringToTimeIllegalArgument() {
        String input = "1992-13-15-03-16-15";
        assertThrows(IllegalArgumentException.class, () -> dateAndTimeUtils.stringToTime(input));
    }

}