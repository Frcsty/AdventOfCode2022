package com.github.frcsty.day;

import com.github.frcsty.registry.DayClass;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class Day_1 extends DayClass {

    private static final Map<UUID, Integer> COUNT = new HashMap<>();

    @Override
    public void process() {
        final AtomicInteger calories = new AtomicInteger(0);

        readInput().lines().forEach(line -> {
            if (line.length() == 0) {
                COUNT.put(UUID.randomUUID(), calories.intValue());
                calories.set(0);
                return;
            }

            calories.addAndGet(Integer.parseInt(line));
        });

        COUNT.put(UUID.randomUUID(), calories.intValue());
    }

    @Override
    public void displayResult() {
        final List<Integer> sorted = COUNT.values().stream().sorted(Comparator.reverseOrder()).toList();

        int count = 0;
        for (int position = 0; position < 3; position++) {
            count += sorted.get(position);
        }

        System.out.println(count);
    }

}
