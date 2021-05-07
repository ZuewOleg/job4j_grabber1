package ru.job4j.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    public final Map<String, Integer> map = new HashMap<>();

    public SqlRuDateTimeParser() {
        insert();
    }

    public void insert() {
        map.put("янв", 1);
        map.put("фев", 2);
        map.put("мар", 3);
        map.put("апр", 4);
        map.put("май", 5);
        map.put("июн", 6);
        map.put("июл", 7);
        map.put("авг", 8);
        map.put("сен", 9);
        map.put("окт", 10);
        map.put("ноя", 11);
        map.put("дек", 12);
    }

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime data;
        // делим строку на день месяц год, часы : минуты
        String[] time = parse.replace(",", "").split(" ");
        int lengthDate = time.length; // длина строки
        // т.к. может быть формат сегодня, 14:53 или 2 янв 19, 11:27
        String[] hm = time[lengthDate - 1].split(":"); // делим строку, для определения часво и минут
        int hours = Integer.parseInt(hm[0]); // часы
        int minute = Integer.parseInt(hm[1]); // минуты
        if (lengthDate == 2) { // если строка имеет 2 элемента в массиве, н-р "сегодня, 14:53"
            data = LocalDateTime.of(LocalDate.now(), LocalTime.of(hours, minute));
            if (parse.contains("вчера")) { // если в строке присутствует "вчера"
                data = data.minusDays(1); // из даты вычитаем день
            }
        } else { // если больше 2х элементов в массиве, н-р "2 янв 19, 11:27"
            int day = Integer.parseInt(time[0]); // день 1 элемент
            int month = map.get(time[1]); // месяц 2 элемент
            int year = Integer.parseInt("20" + time[2]); // год 3 элемент
            data = LocalDateTime.of(year, month, day, hours, minute);
        }
        return data;
    }
}
