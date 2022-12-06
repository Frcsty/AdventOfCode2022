package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.util.concurrent.atomic.AtomicLong;

public final class Day_4 extends DayClass {

    private static final AtomicLong COUNT = new AtomicLong(0);
    private static final AtomicLong SECOND_COUNT = new AtomicLong(0);

    @Override
    public void process() {
        readInput().lines().forEach(line -> {
            final Pair<String, String> pair = argumentLine(line, ",");
            final Pair<Range, Range> range = convert(pair);

            if (Range.contained(range.first(), range.second())) COUNT.getAndIncrement();
        });
    }

    @Override
    public void processSecond() {
        readInput().lines().forEach(line -> {
            final Pair<String, String> pair = argumentLine(line, ",");
            final Pair<Range, Range> range = convert(pair);

            if (Range.overlaps(range.first(), range.second())) SECOND_COUNT.getAndIncrement();
        });
    }

    @Override
    public void displayResult() {
        System.out.println(COUNT.get());
    }

    @Override
    public void displaySecondResult() {
        System.out.println(SECOND_COUNT.get());
    }

    private Pair<Range, Range> convert(final Pair<String, String> input) {
        return new Pair<>(
                new Range(input.first()),
                new Range(input.second())
        );
    }

    private static class Range {

        private final int start;
        private final int end;

        Range(final String input) {
            final String[] split = input.split("-");

            this.start = Integer.parseInt(split[0]);
            this.end = Integer.parseInt(split[1]);
        }

        private static boolean contained(final Range first, final Range second) {
            return (first.start >= second.start && first.end <= second.end) || (second.start >= first.start && second.end <= first.end);
        }

        private static boolean overlaps(final Range first, final Range second) {
            return (first.start >= second.start && first.start <= second.end) || (second.start >= first.start && second.start <= first.end);
        }

    }

}
