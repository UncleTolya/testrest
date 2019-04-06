package ru.tolymhlv.testrest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
class DateAndTimeUtilsTest {

    @Autowired
    DateAndTimeUtils dateAndTimeUtils;

    @Test
    public void stringToTimeCorrect() {
        String input = "1992-04-15-03-15-15";
        LocalDateTime time = dateAndTimeUtils.stringToTime(input);
        assertEquals("1992-04-15T03:15:15", time.toString());
    }

    @Test
    public void stringToTimeIncorrect() {
        String input = "1992-04-15-03-15-15";
        LocalDateTime time = dateAndTimeUtils.stringToTime(input);
        assertNotEquals("1992-04-15T03:15:16", time.toString());
    }

}