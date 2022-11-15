package com.example.java8keesun;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class App {
    public static void main(String[] args) {
        // 보통 기계용
        Instant instant = Instant.now();
        System.out.println("instant = " + instant); // UTC, GMT

        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("zoneId = " + zoneId);
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        System.out.println("zonedDateTime = " + zonedDateTime);

        // 휴먼용
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime.of(1996, Month.MARCH, 06, 0, 0, 0);
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("nowInKorea = " + nowInKorea);

        // 상호 변환 가능
        zonedDateTime.toInstant();

        // 기간 : period(휴먼), duration(머신)
        LocalDate today = LocalDate.now();
        LocalDate nextDob = LocalDate.of(2023, Month.MARCH, 6);

        Period period = Period.between(today, nextDob);
        Period until = today.until(nextDob);
        System.out.println("today = " + today);
        System.out.println("nextDob = " + nextDob);
        System.out.println("period = " + period.getDays());
        System.out.println("until = " + until.get(ChronoUnit.DAYS));

        Instant plus = instant.plus(11, ChronoUnit.SECONDS); // 메소드 실행 결과를 반환 받아야 함
        // instant.plus(11, ChronoUnit.SECONDS); // 아무 일도 일어나지 않음

        Duration between = Duration.between(instant, plus);
        System.out.println("between = " + between.getSeconds());

        // DateTimeFormatter
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(now.format(MMddyyyy));

        LocalDate parse = LocalDate.parse("03/06/1996", MMddyyyy);
        System.out.println("parse = " + parse);

        // legacy Date와의 호환
        Date date = new Date();
        Instant instantDate = date.toInstant();
        Date newDate = Date.from(instantDate);

        // LocalDateTime <--> ZDT
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        LocalDateTime dateTime = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        ZonedDateTime zdt = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
        GregorianCalendar from = GregorianCalendar.from(zdt);

        // TimeZone(legacy) <-> ZoneId
        ZoneId zoneId1 = TimeZone.getTimeZone("PST").toZoneId();
        TimeZone.getTimeZone(zoneId1);
    }
}
