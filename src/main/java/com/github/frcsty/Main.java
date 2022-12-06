package com.github.frcsty;

import com.github.frcsty.registry.DayClass;

import java.util.Calendar;

public class Main {

    private static final Calendar CALENDAR = Calendar.getInstance();

    public static void main(final String[] args) {
        final DayClass clazz = retrieveClass();
        if (clazz == null) {
            throw new RuntimeException("Failed to retrieve class");
        }

        clazz.process();
        clazz.displayResult();

        clazz.processSecond();
        clazz.displaySecondResult();
    }

    private static DayClass retrieveClass() {
        try {
            final Class<?> clazz = Class.forName("com.github.frcsty.day.Day_" + CALENDAR.get(Calendar.DAY_OF_MONTH));
            return (DayClass) clazz.getConstructors()[0].newInstance();
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

}