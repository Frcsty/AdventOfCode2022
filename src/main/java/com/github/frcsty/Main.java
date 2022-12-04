package com.github.frcsty;

import com.github.frcsty.day.Day_1;
import com.github.frcsty.day.Day_2;
import com.github.frcsty.day.Day_3;
import com.github.frcsty.day.Day_4;
import com.github.frcsty.registry.DayClass;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

public class Main {

    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final Set<DayClass> classes = Set.of(
            new Day_1(), new Day_2(), new Day_3(), new Day_4()
    );

    public static void main(final String[] args) {
        final int currentDay = CALENDAR.get(Calendar.DAY_OF_MONTH);
        final Optional<DayClass> optionalClazz = classes.stream()
                .filter(it -> Integer.parseInt(it.getClass().getName().split("_")[1]) == currentDay)
                .findFirst();

        if (optionalClazz.isEmpty()) {
            System.out.println("The entry for day '" + currentDay + "' is empty!");
            return;
        }

        final DayClass clazz = optionalClazz.get();

        System.out.println("Part One Result");
        clazz.process();
        clazz.displayResult();

        System.out.println("Part Two Result");
        clazz.processSecond();
        clazz.displaySecondResult();
    }

}