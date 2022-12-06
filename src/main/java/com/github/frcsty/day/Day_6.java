package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.util.concurrent.atomic.AtomicLong;

public final class Day_6 extends DayClass {

    private static final AtomicLong COUNT = new AtomicLong(0);

    @Override
    public void process() {
        readInput().lines().forEach(line ->
                COUNT.set(count(line, 4))
        );
    }

    @Override
    public void processSecond() {
        readInput().lines().forEach(line ->
                COUNT.set(count(line, 14))
        );
    }

    @Override
    public void displayResult() {
        System.out.println(COUNT.get());
    }

    @Override
    public void displaySecondResult() {
        System.out.println(COUNT.get());
    }

    private int count(final String input, final int length) {
        final StringBuilder builder = new StringBuilder();
        int count = 0;

        for (int index = 0; index < input.length(); index++) {
            final char character = input.charAt(index);

            if (builder.length() >= length) {
                builder.replace(0, 1, "");
            }

            builder.append(character);

            if (!valid(builder.toString(), length)) {
                continue;
            }

            count = index + 1;
            break;
        }

        return count;
    }

    private boolean valid(final String input, final int length) {
        if (input.length() < length) return false;
        return input.length() == input.chars().distinct().count();
    }

}
