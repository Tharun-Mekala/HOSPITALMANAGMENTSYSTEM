package com.pro.hms.resources;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TokenTime {
	public static List<LocalTime> generateTimeIntervals() {
        List<LocalTime> timeList = new ArrayList<>();
        LocalTime startTime1 = LocalTime.of(10, 0);
        LocalTime endTime1 = LocalTime.of(13, 0);

        while (!startTime1.isAfter(endTime1)) {
            timeList.add(startTime1);
            startTime1 = startTime1.plusMinutes(10);
        }
        
        LocalTime startTime2 = LocalTime.of(15, 0);
        LocalTime endTime2 = LocalTime.of(18, 0);

        while (!startTime2.isAfter(endTime2)) {
            timeList.add(startTime2);
            startTime2 = startTime2.plusMinutes(10);
        }

        return timeList;
    }
}
